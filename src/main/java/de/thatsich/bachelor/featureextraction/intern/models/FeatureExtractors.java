package de.thatsich.bachelor.featureextraction.intern.models;

import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureExtractors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class FeatureExtractors implements IFeatureExtractors
{
	// Properties
	private final ListProperty<IFeatureExtractor> featureExtractors = new SimpleListProperty<>(FXCollections.<IFeatureExtractor>observableArrayList());
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<>();

	// Property Getter
	@Override
	public ListProperty<IFeatureExtractor> list()
	{
		return this.featureExtractors;
	}

	@Override
	public ObjectProperty<IFeatureExtractor> selected()
	{
		return this.selectedFeatureExtractor;
	}
}
