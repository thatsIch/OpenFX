package de.thatsich.bachelor.errorgeneration.restricted.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.command.ErrorInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;

public class ErrorDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewError;
	
	// Injects
	@Inject private IErrorEntries errorEntryList;
	
	@Inject ErrorInitCommander initCommander;

	@Override
	protected void initComponents() {
		final ErrorEntry entry = this.errorEntryList.getSelectedErrorEntry();
		if (entry != null) {
			this.nodeImageViewError.imageProperty().setValue(entry.getImage());
			this.log.info("Initialized nodeImageViewError.");
		}
	}

	@Override
	protected void bindComponents() {
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
	}
}
