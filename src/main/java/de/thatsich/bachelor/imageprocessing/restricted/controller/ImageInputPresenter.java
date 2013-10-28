package de.thatsich.bachelor.imageprocessing.restricted.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.command.ImageCommandProvider;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.CopyFileCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.DeleteImageEntryCommand;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageState;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageFileChooser;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

/**
 * Presenter
 * 
 * Represents the input controls for the image database part.
 * Offers basic ability to modify the underlying image-database.
 *  
 *  
 * @author Tran Minh Do
 *
 */
public class ImageInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private Button nodeButtonRemoveImage;
	@FXML private Button nodeButtonResetDatabase;

	// Injects
	@Inject private ImageCommandProvider commander;	
	@Inject private IImageEntries imageEntries;
	@Inject private ImageState imageState;
	@Inject private ImageFileChooser chooser;
 
	// Initialization Implementation 
	@Override
	protected void initComponents() {
	}
 
	// Binding Implementation 
	@Override
	protected void bindComponents() {
		this.bindButtons();
	}

	private void bindButtons() {
		this.nodeButtonRemoveImage.disableProperty().bind(this.imageEntries.selectedImageEntryProperty().isNull());
		this.nodeButtonResetDatabase.disableProperty().bind(this.imageEntries.imageEntriesmageEntryListProperty().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================
	/**
	 * Shows a FileChooser and
	 * adds selected image to model
	 * 
	 * @throws IOException
	 */
	@FXML private void onAddImageAction() throws IOException {

		Path filePath = this.chooser.show();
		if (filePath == null) return;
		this.log.info("Fetched Path from chosen Image.");

		Path copyPath = this.imageState.getImageFolderPath().resolve(filePath.getFileName());
		this.log.info("Created new Path: " + copyPath);

		final CopyFileCommand command = this.commander.createCopyFileCommand(filePath, copyPath);
		command.setOnSucceededCommandHandler(new AddImageEntrySucceededHandler());
		command.start();
		this.log.info("File copied and inserted into EntryList.");
	}

	/**
	 * Removes the currently selected image
	 * 
	 * @throws IOException
	 */
	@FXML private void onRemoveImageAction() throws IOException {
		final ImageEntry choice = this.imageEntries.getSelectedImageEntry();
		this.log.info("Fetched selected ImageEntry.");

		if (choice == null) {
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}

		final DeleteImageEntryCommand command = this.commander.createDeleteImageEntryCommand(choice);
		command.setOnSucceededCommandHandler(new DeleteImageEntrySucceededHandler());
		command.start();
		this.log.info("File deleted and removed from EntryList.");
	}

	/**
	 * Resets the image data base
	 * 
	 * @throws IOException 
	 */
	@FXML private void onResetDatabaseAction() throws IOException {
		final List<ImageEntry> imageEntryList = this.imageEntries.imageEntriesmageEntryListProperty().get();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(imageEntryList.size());
		this.log.info("Initialized Executor for resetting all Errors.");

		for (ImageEntry entry : imageEntryList) {
			final DeleteImageEntryCommand command = this.commander.createDeleteImageEntryCommand(entry);
			command.setOnSucceededCommandHandler(new DeleteImageEntrySucceededHandler());
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("EntryList resetted.");

		executor.execute(new Runnable() {
			@Override public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Handler for what should happen if the Command was successfull 
	 * for adding the image to the input directory.
	 * 
	 * @author Minh
	 */
	private class AddImageEntrySucceededHandler extends ACommandHandler<Path> {
		@Override public void handle(Path value) {
			final Mat copiedMat = Highgui.imread(value.toString(), 0);
			final ImageEntry copy = new ImageEntry(value, copiedMat);
			imageEntries.imageEntriesmageEntryListProperty().get().add(copy);
			log.info("Added copy to ChoiceBoxDisplayImage: " + value.toString());

			imageEntries.selectedImageEntryProperty().set(copy);
			log.info("Set currently selected Image to " + value);
		}
	}

	/**
	 * Handler for what should happen if the Command was successfull 
	 * for deleting the image out of the input directory.
	 * 
	 * @author Minh
	 */
	private class DeleteImageEntrySucceededHandler extends ACommandHandler<ImageEntry> {
		@Override public void handle(ImageEntry deletion) {
			final List<ImageEntry> imageEntryList = imageEntries.imageEntriesmageEntryListProperty().get();

			imageEntryList.remove(deletion);
			log.info("Removed ImageEntry from Database.");

			if (imageEntryList.size() > 0) {
				final ImageEntry first = imageEntries.imageEntriesmageEntryListProperty().get().get(0);
				imageEntries.selectedImageEntryProperty().set(first);
				log.info("Reset Selection to the first.");
			}
			else {
				imageEntries.selectedImageEntryProperty().set(null);
			}
		}
	}
}
