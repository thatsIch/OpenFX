package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.business.model.FeatureSpace;
import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.StringFeatureExtractorConverter;
import de.thatsich.core.opencv.IFeatureExtractor;

public class FeatureInputPresenter extends AFXMLPresenter {
	
	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private Slider nodeSliderFrameSize;

	// Injects
	@Inject private ConfigService config;
	
	@Inject private FeatureSpace features;
	@Inject private ErrorDatabase errorDatabase;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.bindChoiceBoxFeatureExtractor();
		this.bindSliderFrameSize();
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	/**
	 * Bind ChoiceBoxFeatureExtractor to the Model.
	 */
	private void bindChoiceBoxFeatureExtractor() {
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringFeatureExtractorConverter());
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.features.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.features.getSelectedFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
	}	
	
	/**
	 * Set the specific values of the frame size associated with each tick
	 * and sets the labelformatter to fit the representation
	 * 
	 * Java 7 has a bug with the Labels
	 */
	private void bindSliderFrameSize() {
		// write values in power of 2
		// only if slider is let loose
		this.nodeSliderFrameSize.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldBool, Boolean newBool) {
				if (newBool == false) {
					final int frameSize = (int) nodeSliderFrameSize.getValue();
					
					features.setFrameSize((int) Math.pow(2, frameSize));
					config.setLastFrameSizeInt(frameSize);
				}				
			}
		});
		this.log.info("Bound FrameSize to Database.");
		
		this.nodeSliderFrameSize.setValue(this.config.getLastFrameSizeInt());
		this.log.info("Initialized FrameSize from Config.");
		
		// set labels to pwoer of 2
		this.nodeSliderFrameSize.setLabelFormatter(new StringConverter<Double>() {
			@Override public String toString(Double tick) { return String.format("%d", (int) Math.pow(2, tick)); }
			@Override public Double fromString(String paramString) { return 0.0; }
		});
		this.log.info("Formatted FrameSize.");
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	@FXML private void onExtractAction() {
//		this.features.
	}
	
	@FXML private void onRemoveAction() {
		
	}
}
