package de.thatsich.openfx.featureextraction.api.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IFeatureExtractors
{
	ListProperty<IFeatureExtractor> list();

	ObjectProperty<IFeatureExtractor> selected();
}
