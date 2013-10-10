package de.thatsich.bachelor.javafx.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import org.opencv.core.MatOfFloat;

import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.opencv.IFeatureExtractor;

public class FeatureSpace {

	private MatOfFloat featureVectors;
	private MatOfFloat classificationLabels;
	
	// Properties
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	private final ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new ChoiceBox<IFeatureExtractor>().itemsProperty();
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
	public void addFeatureVector(FeatureVector featureVector) {
		this.featureVectors.push_back(featureVector.getFeatureVector().t());
		this.classificationLabels.push_back(featureVector.getClassificationLabel().t());
	}
	
	public MatOfFloat getFeatureVectors() {
		return this.featureVectors;
	}
	
	public MatOfFloat getClassificationLabels() {
		return this.classificationLabels;
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
	
	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setFrameSize(int frameSize) { this.frameSize.set(frameSize); }
	
	// ==================================================
	// Property Implementation
	// ==================================================
	// Frame Size
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	
	// Feature Extractors
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	
	
}
