package de.thatsich.bachelor.featureextraction.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;

public interface IFeatureExtractors {
	public ListProperty<IFeatureExtractor> getFeatureExtractorsProperty();
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty();
	
	// Getter
	public IFeatureExtractor getSelectedFeatureExtractor();
	
	// Setter
	public void setSelectedFeatureExtractor(IFeatureExtractor extractor);
}
