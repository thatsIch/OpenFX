package de.thatsich.bachelor.featureextraction.api.model;

import de.thatsich.bachelor.featureextraction.api.control.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IFeatureVectorSets
{
	// Property Getter
	public ListProperty<FeatureVectorSet> getFeatureVectorSetListProperty();

	public ObjectProperty<FeatureVectorSet> getSelectedFeatureVectorSetProperty();

	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty();

	public IntegerProperty getSelectedIndexProperty();

	// Getter
	public FeatureVectorSet getSelectedFeatureVectorSet();

	// Setter
	public void setSelectedFeatureVectorSet(FeatureVectorSet featureVectorSet);

	public FeatureVector getSelectedFeatureVector();

	public void setSelectedFeatureVector(FeatureVector featureVector);

	public int getSelectedIndex();

	public void setSelectedIndex(int index);
}
