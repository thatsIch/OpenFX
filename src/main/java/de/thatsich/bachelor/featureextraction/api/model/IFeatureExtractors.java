package de.thatsich.bachelor.featureextraction.api.model;

import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IFeatureExtractors
{
	ListProperty<IFeatureExtractor> list();

	ObjectProperty<IFeatureExtractor> selected();
}
