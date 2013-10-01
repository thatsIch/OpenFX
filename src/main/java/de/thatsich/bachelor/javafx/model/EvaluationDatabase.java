package de.thatsich.bachelor.javafx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.model.ErrorDatabase.ErrorEntry;
import de.thatsich.bachelor.opencv.classifier.RandomForest;
import de.thatsich.bachelor.opencv.classifier.SVM;
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
	private final ObjectProperty<IBinaryClassifier> selectedBinaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	
	// ErrorEntry
	private final ObjectProperty<ObservableList<ErrorEntry>> errorEntries = new ChoiceBox<ErrorEntry>().itemsProperty();
	private final ObjectProperty<ErrorEntry> selectedErrorEntry = new SimpleObjectProperty<ErrorEntry>();
	
	
	// Injections
	private final Log log;
	
	@Inject
	private EvaluationDatabase(Log log) {
		this.log = log;
		
		this.initFeatureExtractors();
		this.initBinaryClassifiers();
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
		
		if (this.featureExtractors.get().size() > 0) {
			this.selectedFeatureExtractor.set(this.featureExtractors.get().get(0));
			this.log.info("Initialized with first Feature Extractor.");
		}
	}
	
	private void initBinaryClassifiers() {
		this.binaryClassifiers.get().addAll(
			new RandomForest(),
			new SVM()
		);
		this.log.info("Initialized Binary Classifiers.");
		
		if (this.binaryClassifiers.get().size() > 0) {
			this.selectedBinaryClassifier.set(this.binaryClassifiers.get().get(0));
			this.log.info("Initialized with first Binary Classifier.");
		}
	}
	
	/**
	 * Extracts features of the whole generated test-set
	 * except the one selected. And trains the selected
	 * Binary Classifier with the FeatureVectors
	 */
	// TODO implement trainBC
	public void trainBinaryClassifier() {
		
	}
	
	/**
	 * Tests the trained BinaryClassifier with the selected 
	 * Sample and saving the result into output folder
	 */
	// TODO implement testBC
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
	// Feature Extractors
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	
	// Binary Classifiers
	public ObjectProperty<ObservableList<IBinaryClassifier>> getBinaryClassifiersProperty() { return this.binaryClassifiers; }
	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty() { return this.selectedBinaryClassifier; }
	
}
