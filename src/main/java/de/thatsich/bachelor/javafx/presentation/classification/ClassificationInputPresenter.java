package de.thatsich.bachelor.javafx.presentation.classification;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.business.model.EvaluationDatabase;
import de.thatsich.bachelor.javafx.business.model.ErrorDatabase.ErrorEntry;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.StringFeatureExtractorConverter;
import de.thatsich.core.opencv.ABinaryClassifier;
import de.thatsich.core.opencv.IBinaryClassifier;
import de.thatsich.core.opencv.IFeatureExtractor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

public class ClassificationInputPresenter implements Initializable {
	
	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	@FXML private ChoiceBox<ErrorEntry> nodeChoiceBoxSample;
	@FXML private Slider nodeSliderFrameSize;

	// Injects
	@Inject private Log log;
	@Inject private ErrorDatabase errorDatabase;
	@Inject private EvaluationDatabase evalDatabase;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.bindChoiceBoxBinaryClassifiers();
		this.bindChoiceBoxFeatureExtractor();
		this.bindChoiceBoxSamples();
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
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.evalDatabase.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.evalDatabase.getSelectedFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
	}
	
	/**
	 * Bind ChoiceBoxBinaryClassifier to the Model.
	 */
	private void bindChoiceBoxBinaryClassifiers() {
		this.nodeChoiceBoxBinaryClassifier.setConverter(ABinaryClassifier.CONVERTER);
		this.log.info("Set up ChoiceBoxBinaryClassifiers for proper name display.");
		
		this.nodeChoiceBoxBinaryClassifier.itemsProperty().bindBidirectional(this.evalDatabase.getBinaryClassifiersProperty());
		this.nodeChoiceBoxBinaryClassifier.valueProperty().bindBidirectional(this.evalDatabase.getSelectedBinaryClassifierProperty());
		this.log.info("Bound ChoiceBoxBinaryClassifier to Model.");
	}
	
	/**
	 * Bind ChoiceBoxSample to Model
	 * and link both Models
	 */
	private void bindChoiceBoxSamples() {
		this.nodeChoiceBoxSample.setConverter(ErrorDatabase.ErrorEntry.CONVERTER);
		this.log.info("Set up ErrorEntryStringConverter for proper name display.");
		
		this.nodeChoiceBoxSample.itemsProperty().bindBidirectional(this.evalDatabase.getErrorEntriesProperty());
		this.nodeChoiceBoxSample.valueProperty().bindBidirectional(this.evalDatabase.getSelectedErrorEntryProperty());
		this.log.info("Bound ChoiceBoxDisplayedError to Model.");
		
		this.evalDatabase.getErrorEntriesProperty().bind(this.errorDatabase.getErrorEntriesProperty());
		this.log.info("Bound both ErrorEntryLists in Models together.");
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
					evalDatabase.setFrameSize((int) Math.pow(2, nodeSliderFrameSize.getValue()));
				}				
			}
		});
		this.evalDatabase.setFrameSize((int) Math.pow(2, this.nodeSliderFrameSize.getValue()));
		
		// set labels to pwoer of 2
		this.nodeSliderFrameSize.setLabelFormatter(new StringConverter<Double>() {
			@Override public String toString(Double tick) { return String.format("%d", (int) Math.pow(2, tick)); }
			@Override public Double fromString(String paramString) { return 0.0; }
		});
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	@FXML private void onTrainAction() {
		this.evalDatabase.trainBinaryClassifier();
	}
	
	@FXML private void onTestAction() {
		this.evalDatabase.testBinaryClassifier();
//		Mat ones = new Mat(3, 3, CvType.CV_8U, new Scalar(100));
//		Mat twos = new Mat(3, 3, CvType.CV_8U, new Scalar(255));
//		Images.show(ones);
//		Images.show(twos);
//		Mat thre = new Mat();
//		Core.absdiff(ones, twos, thre);
//		Images.show(thre);
	}
}
