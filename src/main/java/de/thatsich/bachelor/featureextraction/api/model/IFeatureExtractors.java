package de.thatsich.bachelor.featureextraction.api.model;

import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IFeatureExtractors
{
	public ListProperty<IFeatureExtractor> getFeatureExtractorsProperty();

	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty();

	// Getter
	public IFeatureExtractor getSelectedFeatureExtractor();

	// Setter
	public void setSelectedFeatureExtractor(IFeatureExtractor extractor);
}
