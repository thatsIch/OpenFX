package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyLongProperty;
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
	private final ReadOnlyListWrapper<IFeatureVector> featureVectors;

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
	public ReadOnlyStringProperty preProcessorName()
	{
		return this.config.preProcessorName;
	}

	@Override
	public ReadOnlyIntegerProperty tileSize()
	{
		return this.config.tileSize;
	}

	@Override
	public ReadOnlyLongProperty trainTime()
	{
		return this.config.trainTime;
	}

	@Override
	public ReadOnlyListProperty<IFeatureVector> vectors()
	{
		return this.featureVectors.getReadOnlyProperty();
	}

	@Override
	public IFeature merge(IFeature that)
	{
		if (this.equals(that))
		{
			this.featureVectors.addAll(that.vectors());

			final long oldTrainTime = this.config.trainTime.get();
			final long addTrainTime = that.getConfig().trainTime.get();
			final long newTrainTime = oldTrainTime + addTrainTime;

			this.config.trainTime.set(newTrainTime);
		}

		return this;
	}

	@Override
	public FeatureConfig getConfig()
	{
		return this.config;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof IFeature)
		{
			final IFeature that = (IFeature) other;
			final boolean equalExtractor = this.extractorName().get().equals(that.extractorName().get());
			final boolean equalClass = this.className().get().equals(that.className().get());
			final boolean equalTileSize = this.tileSize().get() == that.tileSize().get();

			return equalExtractor && equalClass && equalTileSize;
		}

		return false;
	}


}
