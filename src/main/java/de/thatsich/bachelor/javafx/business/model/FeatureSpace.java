package de.thatsich.bachelor.javafx.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.IFeatureExtractor;

public class FeatureSpace {

	private MatOfFloat featureVectors;
	private MatOfFloat classificationLabels;
	
	// Properties
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	private final ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new SimpleObjectProperty<ObservableList<IFeatureExtractor>>();
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	private final ObjectProperty<ObservableList<FeatureVector>> featureVectorList = new SimpleObjectProperty<ObservableList<FeatureVector>>();
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<FeatureVector>();
	
	// Injections
	private final Log log;
	
	@Inject
	public FeatureSpace(Log log) {
		this.log = log;
		
		ObservableList<FeatureVector> featureVectors = FXCollections.observableArrayList();
		ObservableList<IFeatureExtractor> featureExtractors = FXCollections.observableArrayList();
		
		this.featureVectorList.set(featureVectors);
		this.featureExtractors.set(featureExtractors);
		
		this.initFeatureExtractors();
	}
	
	
//	public void addFeatureVector(FeatureVector featureVector) {
////		this.featureVectors.push_back(featureVector.getFeatureVector().t());
////		this.classificationLabels.push_back(featureVector.getClassificationLabel().t());
//	}
	
	public MatOfFloat getFeatureVectors() {
		return this.featureVectors;
	}
	
	public MatOfFloat getClassificationLabels() {
		return this.classificationLabels;
	}
	

	/**
	 * Initialize Feature Extracotrs
	 * - adding all known to the list
	 * - select the first in list if available
	 */
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
	
	// ==================================================
	// Getter Implementation
	// ==================================================
	
	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setFrameSize(int frameSize) { this.frameSize.set(frameSize); }
	
	// ==================================================
	// Modifier Implementation
	// ==================================================
	public void addFeatureVector(FeatureVector featureVector) { this.featureVectorList.get().add(featureVector); }
	
	// ==================================================
	// Property Implementation
	// ==================================================
	// Frame Size
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	
	// Feature Extractors
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	public ObjectProperty<ObservableList<FeatureVector>> getFeatureVectorListProperty() { return this.featureVectorList; }
	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty() { return this.selectedFeatureVector; }
}
