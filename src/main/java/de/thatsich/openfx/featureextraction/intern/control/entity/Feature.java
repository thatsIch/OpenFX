package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public class Feature implements IFeature
{
	// Properties
	private final FeatureConfig config;
	private final ReadOnlyListProperty<IFeatureVector> featureVectors;

	public Feature(final FeatureConfig config, final List<IFeatureVector> featureVectors)
	{
		final ObservableList<IFeatureVector> vector = FXCollections.observableArrayList(featureVectors);

		this.config = config;
		this.featureVectors = new ReadOnlyListWrapper<>(vector);
	}

	@Override
	public ReadOnlyStringProperty extractorName()
	{
		return this.config.extractorName;
	}

	@Override
	public ReadOnlyStringProperty className()
	{
		return this.config.className;
	}

	@Override
	public ReadOnlyIntegerProperty tileSize()
	{
		return this.config.tileSize;
	}

	@Override
	public ReadOnlyListProperty<IFeatureVector> vectors()
	{
		return this.featureVectors;
	}

	@Override
	public FeatureConfig getConfig()
	{
		return this.config;
	}
}
