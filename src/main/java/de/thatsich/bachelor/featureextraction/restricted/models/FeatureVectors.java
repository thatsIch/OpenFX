package de.thatsich.bachelor.featureextraction.restricted.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;

public class FeatureVectors {
	
	// Properties
	private final ListProperty<FeatureVector> featureVectorList = new SimpleListProperty<FeatureVector>(FXCollections.<FeatureVector>observableArrayList());
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<FeatureVector>();
	
	// Property Getter
	public ListProperty<FeatureVector> getFeatureVectorListProperty() { return this.featureVectorList; }
	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty() { return this.selectedFeatureVector; }
}
