package de.thatsich.bachelor.javafx.presentation.image;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ImageDatabase;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.bachelor.service.ImageSpaceService;
import de.thatsich.core.javafx.AFXMLPresenter;
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
	@Inject private ImageSpaceService fileSystemService;
	
	@Inject private ImageDatabase images;
	@Inject private ImageFileChooser chooser;
	
	// ================================================== 
	// Initializable Implementation 
	// ==================================================
	@Override
	public void initialize(URL url, ResourceBundle bundle) {

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
		
		this.fileSystemService.copyFile(new AddImageSucceededHandler(), filePath, copyPath);
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
		
		this.fileSystemService.deleteImageEntry(new DeleteSucceededHandler(), choice);
		this.log.info("File deleted and removed from EntryList.");
	}
	
	/**
	 * Resets the image data base
	 * 
	 * @throws IOException 
	 */
	@FXML private void onResetDatabaseAction() throws IOException {
		for (ImageEntry entry : this.images.getImageEntries()) {
			this.fileSystemService.deleteImageEntry(new DeleteSucceededHandler(), entry);
		}
		this.log.info("EntryList resetted.");
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
