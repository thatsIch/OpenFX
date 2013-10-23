package de.thatsich.bachelor.classificationtraining.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import de.thatsich.core.javafx.AFXMLPresenter;

public class TrainDisplayPresenter extends AFXMLPresenter {

	@FXML private Label nodeLabelClassifierName;
	@FXML private Label nodeLabelExtractorName;
	@FXML private Label nodeLabelFrameSize;
	@FXML private Label nodeLabelErrorName;
	@FXML private Label nodeLabelID;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
	}

}
