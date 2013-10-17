package de.thatsich.bachelor.javafx.business.model;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.opencv.IFeatureExtractor;

/**
 * FeatureSpace Class
 * holds all feature vectors and feature extractors
 * 
 * @author Minh
 *
 */
public class FeatureSpace {
	
	// Properties
	private final ObjectProperty<Path> featureVectorFolderPath = new SimpleObjectProperty<Path>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	private final ListProperty<IFeatureExtractor> featureExtractors = new SimpleListProperty<IFeatureExtractor>(FXCollections.<IFeatureExtractor>observableArrayList());
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	private final ListProperty<FeatureVector> featureVectorList = new SimpleListProperty<FeatureVector>(FXCollections.<FeatureVector>observableArrayList());
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<FeatureVector>();
	
//	public void addFeatureVector(FeatureVector featureVector) {
////		this.featureVectors.push_back(featureVector.getFeatureVector().t());
////		this.classificationLabels.push_back(featureVector.getClassificationLabel().t());
//	}


	// ==================================================
	// Property Implementation
	// ==================================================
	// Folder
	public ObjectProperty<Path> getFeatureVectorFolderPathProperty() { return this.featureVectorFolderPath; }
	
	// Frame Size
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	
	// Feature Extractors
	public ListProperty<IFeatureExtractor> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	public ListProperty<FeatureVector> getFeatureVectorListProperty() { return this.featureVectorList; }
	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty() { return this.selectedFeatureVector; }
}
