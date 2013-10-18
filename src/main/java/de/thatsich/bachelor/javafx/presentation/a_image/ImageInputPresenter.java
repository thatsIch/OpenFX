package de.thatsich.bachelor.javafx.presentation.a_image;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.command.CommandFactory;
import de.thatsich.bachelor.javafx.business.command.CopyFileCommand;
import de.thatsich.bachelor.javafx.business.command.DeleteImageEntryCommand;
import de.thatsich.bachelor.javafx.business.model.ImageEntries;
import de.thatsich.bachelor.javafx.business.model.ImageState;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.ImageFileChooser;

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

	// Injects
	@Inject private CommandFactory commander;	
	@Inject private ImageEntries imageEntries;
	@Inject private ImageState imageState;
	@Inject private ImageFileChooser chooser;

	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
	}
	
	// ================================================== 
	// View Implementation 
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
		
		Path copyPath = this.imageState.getImageInputFolderPathProperty().get().resolve(filePath.getFileName());
		this.log.info("Created new Path: " + copyPath);
		
		final AddImageSucceededHandler handler = new AddImageSucceededHandler();
		final CopyFileCommand command = this.commander.createCopyFileCommand(filePath, copyPath);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("File copied and inserted into EntryList.");
	}
	
	/**
	 * Removes the currently selected image
	 * 
	 * @throws IOException
	 */
	@FXML private void onRemoveImageAction() throws IOException {
		final ImageEntry choice = this.imageEntries.getSelectedImageEntryProperty().get();
		this.log.info("Fetched selected ImageEntry.");
		
		if (choice == null) {
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}
		
		final DeleteSucceededHandler handler = new DeleteSucceededHandler();
		final DeleteImageEntryCommand command = this.commander.createDeleteImageEntryCommand(choice);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("File deleted and removed from EntryList.");
	}
	
	/**
	 * Resets the image data base
	 * 
	 * @throws IOException 
	 */
	@FXML private void onResetDatabaseAction() throws IOException {
		final List<ImageEntry> imageEntryList = this.imageEntries.getImageEntryListProperty().get();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(imageEntryList.size());
		this.log.info("Initialized Executor for resetting all Errors.");
		
		for (ImageEntry entry : imageEntryList) {
			final DeleteSucceededHandler handler = new DeleteSucceededHandler();
			final DeleteImageEntryCommand command = this.commander.createDeleteImageEntryCommand(entry);
			command.setOnSucceeded(handler);
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("EntryList resetted.");
		
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				System.gc();
			}
		});
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================
	
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for adding the image to the input directory.
	 * 
	 * @author Minh
	 */
	private class AddImageSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			Path copiedPath = (Path) event.getSource().getValue();
			ImageEntry copy = new ImageEntry(copiedPath);
			imageEntries.getImageEntryListProperty().get().add(copy);
			log.info("Added copy to ChoiceBoxDisplayImage: " + copiedPath.toString());
			
			imageEntries.getSelectedImageEntryProperty().set(copy);
			log.info("Set currently selected Image to " + copiedPath);
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for adding the image to the input directory.
	 * 
	 * @author Minh
	 */
	private class DeleteSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final ImageEntry deletion = imageEntries.getSelectedImageEntryProperty().get();
			final List<ImageEntry> imageEntryList = imageEntries.getImageEntryListProperty().get();
			
			imageEntryList.remove(deletion);
			log.info("Removed ImageEntry from Database.");
			
			if (imageEntryList.size() > 0) {
				final ImageEntry first = imageEntries.getImageEntryListProperty().get().get(0);
				imageEntries.getSelectedImageEntryProperty().set(first);
				log.info("Reset Selection to the first.");
			}
		}
	}
}