package de.thatsich.bachelor.featureextraction.api.model;

import de.thatsich.bachelor.featureextraction.api.control.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IFeatureVectorSets
{
	ListProperty<FeatureVectorSet> list();

	ObjectProperty<FeatureVectorSet> selectedSet();

	ObjectProperty<FeatureVector> selected();

	IntegerProperty index();
}
