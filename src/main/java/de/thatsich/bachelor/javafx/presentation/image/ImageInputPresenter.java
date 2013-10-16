package de.thatsich.bachelor.javafx.presentation.image;

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
import de.thatsich.bachelor.javafx.business.command.DeleteImageEntryCommand;
import de.thatsich.bachelor.javafx.business.model.ImageDatabase;
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
	@Inject private ImageDatabase images;
	@Inject private ImageFileChooser chooser;
	
	// ================================================== 
	// Initializable Implementation 
	// ==================================================
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.initImages();
	}
	
	/**
	 * Initialize all ImageEntries
	 */
	private void initImages() {
		final Path imageInputPath = this.images.getInputPath();
		final InitImageSucceededHandler handler = new InitImageSucceededHandler();
		
		this.commander.createInitImageEntryCommand(handler, imageInputPath).start();
		this.log.info("Inialized ImageEntrie Retrieval.");
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
		
		Path copyPath = this.images.getInputPath().resolve(filePath.getFileName());
		this.log.info("Created new Path: " + copyPath);
		
		this.commander.createCopyFileCommand(new AddImageSucceededHandler(), filePath, copyPath).start();
		this.log.info("File copied and inserted into EntryList.");
	}
	
	/**
	 * Removes the currently selected image
	 * 
	 * @throws IOException
	 */
	@FXML private void onRemoveImageAction() throws IOException {
		final ImageEntry choice = this.images.getSelectedImageEntry();
		this.log.info("Fetched selected ImageEntry.");
		
		if (choice == null) {
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}
		
		this.commander.createDeleteImageEntryCommand(new DeleteSucceededHandler(), choice).start();
		this.log.info("File deleted and removed from EntryList.");
	}
	
	/**
	 * Resets the image data base
	 * 
	 * @throws IOException 
	 */
	@FXML private void onResetDatabaseAction() throws IOException {
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(this.images.getImageEntries().size());
		this.log.info("Initialized Executor for resetting all Errors.");
		
		for (ImageEntry entry : this.images.getImageEntries()) {
			DeleteImageEntryCommand command = this.commander.createDeleteImageEntryCommand(new DeleteSucceededHandler(), entry);
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
	@SuppressWarnings("unchecked")
	private class InitImageSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			
			final List<ImageEntry> commandResult = (List<ImageEntry>) event.getSource().getValue();
			log.info("Retrieved ImageEntr List");
			
			images.getImageEntriesProperty().get().addAll(commandResult);
		}
		
	}
	
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
			images.addImageEntry(copy);
			log.info("Added copy to ChoiceBoxDisplayImage: " + copiedPath.toString());
			
			images.setImageEntry(copy);
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
			final ImageEntry deletion = images.getSelectedImageEntry();
			
			images.removeImageEntry(deletion);
			log.info("Removed ImageEntry from Database.");
			
			images.resetSelection();
			log.info("Reset Selection to the first.");
		}
	}
}
