package de.thatsich.bachelor.classificationtraining.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.BinaryClassifications;
import de.thatsich.core.javafx.AFXMLPresenter;

public class TrainDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private Label nodeLabelClassificationName;
	@FXML private Label nodeLabelExtractorName;
	@FXML private Label nodeLabelFrameSize;
	@FXML private Label nodeLabelErrorName;
	@FXML private Label nodeLabelID;
	
	// Injects
	@Inject private BinaryClassifications binaryClassifications;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		this.initLabels();
	}

	/**
	 * Binds all labels to the changing FeatureVector
	 */
	private void initLabels() {
		this.binaryClassifications.getSelectedBinaryClassificationProperty().addListener(new ChangeListener<IBinaryClassification>() {
			@Override public void changed(ObservableValue<? extends IBinaryClassification> observable, IBinaryClassification oldValue, IBinaryClassification newValue) {
				if (newValue != null) {
					nodeLabelClassificationName.setText(newValue.getClassificationNameProperty().getValue().toString());
					nodeLabelExtractorName.setText(newValue.getExtractorNameProperty().getValue().toString());
					nodeLabelFrameSize.setText(newValue.getFrameSizeProperty().getValue().toString());
					nodeLabelErrorName.setText(newValue.getErrorNameProperty().getValue().toString());
					nodeLabelID.setText(newValue.getIdProperty().getValue().toString());
				} else {
					nodeLabelClassificationName.setText(null);
					nodeLabelExtractorName.setText(null);
					nodeLabelFrameSize.setText(null);
					nodeLabelErrorName.setText(null);
					nodeLabelID.setText(null);
				}
			}
		});
		this.log.info("Bound Labels to changing BinaryClassification.");
	}
}
