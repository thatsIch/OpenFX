package de.thatsich.bachelor.featureextraction.restricted.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.core.opencv.IFeatureExtractor;

public class FeatureExtractors {

	// Properties
	private final ListProperty<IFeatureExtractor> featureExtractors = new SimpleListProperty<IFeatureExtractor>(FXCollections.<IFeatureExtractor>observableArrayList());
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
	// Property Getter
	public ListProperty<IFeatureExtractor> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
}
