package de.thatsich.bachelor.javafx.presentation.image;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ImageDatabase;
import de.thatsich.bachelor.javafx.business.model.ImageDatabase.ImageEntry;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.ImageFileChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class ImageInputPresenter implements Initializable {

	// Nodes
	@FXML private ChoiceBox<ImageEntry> nodeChoiceBoxDisplayImage;
	
	// Injects
	@Inject private Log log;
	@Inject private ImageDatabase images;
	@Inject private ImageFileChooser chooser;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.nodeChoiceBoxDisplayImage.setConverter(ImageDatabase.ImageEntry.CONVERTER);
		this.log.info("Set up ItemEntryStringConverter for proper name display.");
		
		this.nodeChoiceBoxDisplayImage.itemsProperty().bindBidirectional(this.images.getImageEntriesProperty());
		this.nodeChoiceBoxDisplayImage.valueProperty().bindBidirectional(this.images.getImageEntryProperty());
		this.log.info("Bound ChoiceBoxDisplayImage to Model.");
	}

	/**
	 * Shows a FileChooser and
	 * adds selected image to model
	 * 
	 * @throws IOException
	 */
	@FXML private void onAddImageAction() throws IOException {
		
		Path filePath = this.chooser.show();
		if (filePath == null) return;
	
		this.images.addImage(filePath);
	}
	
	/**
	 * Removes the currently selected image
	 * 
	 * @throws IOException
	 */
	@FXML private void onRemoveImageAction() throws IOException {
		this.images.removeSelectedImage();
	}
	
	/**
	 * Resets the image data base
	 * 
	 * @throws IOException 
	 */
	@FXML private void onResetDatabaseAction() throws IOException {
		this.images.resetImageDatabase();
	}
}
