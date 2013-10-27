package de.thatsich.bachelor.imageprocessing.restricted.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.core.javafx.AFXMLPresenter;

public class ImageDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewInput;
	
	// Injects
	@Inject private ImageEntries imageEntries;

	@Override
	protected void initComponents() {
		ImageEntry entry = this.imageEntries.getSelectedImageEntryProperty().get();
		if (entry != null) {
			this.nodeImageViewInput.imageProperty().setValue(entry.getImage());
			this.log.info("Initialized nodeImageViewInput.");
		}
	}

	@Override
	protected void bindComponents() {
		this.imageEntries.getSelectedImageEntryProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue) {
				if (newValue != null) {
					nodeImageViewInput.imageProperty().setValue(newValue.getImage());
				} else {
					nodeImageViewInput.imageProperty().set(null);
				}
			}
		});
		this.log.info("Bound ImageView to Model.");
	}

}
