package de.thatsich.bachelor.javafx.presentation.c_feature;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureVectors;
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
	
	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.initLabels();
	}

	/**
	 * Binds all labels to the changing FeatureVector
	 */
	private void initLabels() {
		this.nodeLabelFeatureVector.setTooltip(new Tooltip());
		this.log.info("Initialized Tooltip of LabelFeatureVector.");
		
		this.featureVectors.getSelectedFeatureVectorProperty().addListener(new ChangeListener<FeatureVector>() {
			@Override public void changed(ObservableValue<? extends FeatureVector> observable, FeatureVector oldValue, FeatureVector newValue) {
				if (newValue != null) {
					nodeLabelClassName.setText(newValue.getClassNameProperty().get());
					nodeLabelExtractorName.setText(newValue.getExtractorNameProperty().get());
					nodeLabelFrameSize.setText(newValue.getFrameSizeProperty().getValue().toString());
					nodeLabelID.setText(newValue.getIdProperty().get());
					nodeLabelFeatureVector.setText(newValue.getFeatureVectorProperty().get().dump());
					nodeLabelFeatureVector.getTooltip().setText(newValue.getFeatureVectorProperty().get().dump());
					nodeLabelFeatureLabel.setText(newValue.getFeatureLabelProperty().get().dump());			
				} else {
					nodeLabelClassName.setText(null);
					nodeLabelExtractorName.setText(null);
					nodeLabelFrameSize.setText(null);
					nodeLabelID.setText(null);
					nodeLabelFeatureVector.setText(null);
					nodeLabelFeatureVector.getTooltip().setText(null);
					nodeLabelFeatureLabel.setText(null);
				}
			}
		});
		this.log.info("Bound Labels to changing FeatureVector");
	}
}
