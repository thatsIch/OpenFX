package de.thatsich.bachelor.javafx.presentation.a_image;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ImageEntries;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.AFXMLPresenter;

public class ImageDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewInput;
	
	// Injects
	@Inject private ImageEntries imageEntries;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.imageEntries.getSelectedImageEntryProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue) {
				if (newValue != null) {
					nodeImageViewInput.imageProperty().setValue(newValue.getImage());
				}
			}
		});
		this.log.info("Bound ImageView to Model.");
		
		ImageEntry entry = this.imageEntries.getSelectedImageEntryProperty().get();
		if (entry != null) {
			this.nodeImageViewInput.imageProperty().setValue(entry.getImage());
			this.log.info("Initialized nodeImageViewInput.");
		}
	}

}
