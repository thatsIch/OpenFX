package de.thatsich.bachelor.javafx.presentation.image;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ImageDatabase;
import de.thatsich.bachelor.javafx.business.model.ImageDatabase.ImageEntry;
import de.thatsich.core.Log;

public class ImageDisplayPresenter implements Initializable {

	// Nodes
	@FXML private ImageView nodeImageViewInput;
	
	// Injects
	@Inject private Log log;
	@Inject private ImageDatabase images;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.images.getImageEntryProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue) {
				if (newValue != null) {
					nodeImageViewInput.imageProperty().setValue(newValue.getImage());
				}
			}
		});
		this.log.info("Bound ImageView to Model.");
		
		ImageEntry entry = this.images.getImageEntryProperty().get();
		if (entry != null) {
			this.nodeImageViewInput.imageProperty().setValue(entry.getImage());
			this.log.info("Initialized nodeImageViewInput.");
		}
	}

}
