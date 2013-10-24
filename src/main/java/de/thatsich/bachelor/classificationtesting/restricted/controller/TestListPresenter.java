package de.thatsich.bachelor.classificationtesting.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.core.javafx.AFXMLPresenter;

public class TestListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private TableView<BinaryPrediction> nodeTableViewBinaryPredictionList;
	
	// Injects
	@Inject private BinaryPredictions binaryPredictions;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		this.bindTableView();
	}
	
	private void bindTableView() {
		this.bindTableViewModel();
		this.bindTableViewSelection();
		this.bindTableViewCellValue();
	}

	private void bindTableViewModel() {
		this.nodeTableViewBinaryPredictionList.itemsProperty().bind(this.binaryPredictions.getBinaryPredictionListProperty());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelection() {
	}

	private void bindTableViewCellValue() {
	
	}

}
