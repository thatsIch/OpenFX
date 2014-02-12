package de.thatsich.bachelor.imageprocessing.intern.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.intern.command.ImageInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;

public class ImageDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewInput;
	
	// Injects
	@Inject private IImageEntries imageEntries;
	
	@Inject ImageInitCommander initCommander;

	@Override
	protected void initComponents() {
		ImageEntry entry = this.imageEntries.selectedImageEntryProperty().get();
		if (entry != null) {
			final Image entryImage = Images.toImage(entry.getImageMat());
			this.nodeImageViewInput.imageProperty().setValue(entryImage);
			this.log.info("Initialized nodeImageViewInput.");
		}
	}

	@Override
	protected void bindComponents() {
		this.imageEntries.selectedImageEntryProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue) {
				nodeImageViewInput.imageProperty().setValue((newValue != null) ? Images.toImage(newValue.getImageMat()) : null);
			}
		});
		this.log.info("Bound ImageView to Model.");
	}
}