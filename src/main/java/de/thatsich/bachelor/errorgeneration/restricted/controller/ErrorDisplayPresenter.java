package de.thatsich.bachelor.errorgeneration.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.models.ErrorEntries;
import de.thatsich.core.javafx.AFXMLPresenter;

public class ErrorDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewError;
	
	// Injects
	@Inject private ErrorEntries errorEntryList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.errorEntryList.getSelectedErrorEntryProperty().addListener(new ChangeListener<ErrorEntry>() {
			@Override public void changed(ObservableValue<? extends ErrorEntry> observable, ErrorEntry oldValue, ErrorEntry newValue) {
				if (newValue != null) {
					nodeImageViewError.imageProperty().setValue(newValue.getImage());
					log.info("Selected new ErrorEntry.");
				} else {
					nodeImageViewError.imageProperty().set(null);
					log.info("Set Image to null.");
				}
			}
		});
		this.log.info("Bound nodeImageViewError to Model.");
		
		ErrorEntry entry = this.errorEntryList.getSelectedErrorEntryProperty().get();
		if (entry != null) {
			this.nodeImageViewError.imageProperty().setValue(entry.getImage());
			this.log.info("Initialized nodeImageViewError.");
		}
	}

}
