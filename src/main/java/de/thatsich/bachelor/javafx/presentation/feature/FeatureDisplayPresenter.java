package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.FeatureVectors;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.javafx.AFXMLPresenter;

public class FeatureDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private Label nodeLabelClassName;
	@FXML private Label nodeLabelExtractorName;
	@FXML private Label nodeLabelFrameSize;
	@FXML private Label nodeLabelID;
	@FXML private Label nodeLabelFeatureVector;
	@FXML private Label nodeLabelFeatureLabel;
	
	// Injects
	@Inject private FeatureVectors featureVectors;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.initLabels();
	}

	private void initLabels() {
		this.featureVectors.getSelectedFeatureVectorProperty().addListener(new ChangeListener<FeatureVector>() {

			@Override public void changed(ObservableValue<? extends FeatureVector> observable, FeatureVector oldValue, FeatureVector newValue) {
				nodeLabelClassName.setText(newValue.getClassNameProperty().get());
				nodeLabelExtractorName.setText(newValue.getExtractorNameProperty().get());
				nodeLabelFrameSize.setText(newValue.getFrameSizeProperty().getValue().toString());
				nodeLabelID.setText(newValue.getIdProperty().get());
				nodeLabelFeatureVector.setText(newValue.getFeatureVectorProperty().get().dump());
				nodeLabelFeatureLabel.setText(newValue.getFeatureLabelProperty().get().dump());
			}
		});
	}
}
