package de.thatsich.bachelor.javafx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.model.ErrorDatabase.ErrorEntry;
import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.classifier.IBinaryClassifier;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

public class EvaluationDatabase {
	
	// Properties
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	// Feature Extractor
	private final ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new ChoiceBox<IFeatureExtractor>().itemsProperty();
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
	// Binary Classifier
	private final ObjectProperty<ObservableList<IBinaryClassifier>> binaryClassifiers = new ChoiceBox<IBinaryClassifier>().itemsProperty();
	private final ObjectProperty<IFeatureExtractor> selectedBinaryClassifier = new SimpleObjectProperty<IFeatureExtractor>();
	
	// ErrorEntry
	private final ObjectProperty<ObservableList<ErrorEntry>> errorEntries = new ChoiceBox<ErrorEntry>().itemsProperty();
	private final ObjectProperty<ErrorEntry> selectedErrorEntry = new SimpleObjectProperty<ErrorEntry>();
	
	
	// Injections
	private final Log log;
	
	@Inject
	private EvaluationDatabase(Log log) {
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
	
	/**
	 * Extracts features of the whole generated test-set
	 * except the one selected. And trains the selected
	 * Binary Classifier with the FeatureVectors
	 */
	public void trainBinaryClassifier() {
		
	}
	
	/**
	 * Tests the trained BinaryClassifier with the selected 
	 * Sample and saving the result into output folder
	 */
	public void testBinaryClassifier() {
		
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================	
	public int getFrameSize() { return this.frameSize.get(); }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setFrameSize(int frameSize) { this.frameSize.set(frameSize); } 
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
}
