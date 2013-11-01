package de.thatsich.bachelor.featureextraction.api.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

public interface IFeatureVectorSets {
	// Property Getter
	public ListProperty<FeatureVectorSet> getFeatureVectorSetListProperty();
	public ObjectProperty<FeatureVectorSet> getSelectedFeatureVectorSetProperty();
	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty();
	public IntegerProperty getSelectedIndexProperty();
	
	// Getter
	public FeatureVectorSet getSelectedFeatureVectorSet();
	public FeatureVector getSelectedFeatureVector();
	public int getSelectedIndex();
	
	// Setter
	public void setSelectedFeatureVectorSet(FeatureVectorSet featureVectorSet);
	public void setSelectedFeatureVector(FeatureVector featureVector);
	public void setSelectedIndex(int index);
}
