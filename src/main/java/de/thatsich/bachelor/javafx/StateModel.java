package de.thatsich.bachelor.javafx;

import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;


/**
 * Stores the State of the application.
 * 
 * @author Tran Minh Do
 *
 */
public class StateModel {
	
	// Properties
	final private ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new ChoiceBox<IFeatureExtractor>().itemsProperty();
	
	final private ObjectProperty<IFeatureExtractor> featureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
	final private IntegerProperty frameSize = new SimpleIntegerProperty();
	final private IntegerProperty threshold = new SimpleIntegerProperty();
	
	// Injects
	final private Log log;
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Inject
	public StateModel(Log log) throws IOException {
		this.log = log;

		this.initFeatureExtractors();
	}
	
	private void initFeatureExtractors() {
		this.featureExtractors.get().addAll(
			new Gradient(),
			new GrayLevelCooccurenceHistogram(),
			new HuMoments(),
			new LocalBinaryPatternHistogram(),
			new Mean(),
			new Variance()
		);
		this.log.info("Initialized Feature Extractors.");
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
	public int getFrameSize() { return this.frameSize.get(); }
	public int getThreshold() { return this.threshold.get(); }
	

	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setFeatureExtractors(ObservableList<IFeatureExtractor> featureExtractors) { this.featureExtractors.set(featureExtractors); }
	
	public void setFeatureExtractor(IFeatureExtractor featureExtractor) { this.featureExtractor.set(featureExtractor); }
	
	public void setFrameSize(int size) { this.frameSize.set(size); }
	public void setThreshold(int threshold) { this.threshold.set(threshold); }
	
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }

	public ObjectProperty<IFeatureExtractor> getFeatureExtractorProperty() { return this.featureExtractor; }
	
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	public IntegerProperty getThresholdProperty() { return this.threshold; }
}
