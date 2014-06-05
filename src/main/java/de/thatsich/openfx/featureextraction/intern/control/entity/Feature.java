package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;

import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public class Feature implements IFeature
{
	// Properties
	private final FeatureConfig config;
	private final List<IFeatureVector> featureVectors;

	public Feature(final FeatureConfig config, final List<IFeatureVector> featureVectors)
	{
		this.config = config;
		this.featureVectors = featureVectors;
	}

	@Override
	public String extractorName()
	{
		return this.config.extractorName;
	}

	@Override
	public String className()
	{
		return this.config.className;
	}

	@Override
	public int tileSize()
	{
		return this.config.tileSize;
	}

	@Override
	public List<IFeatureVector> vectors()
	{
		return this.featureVectors;
	}

	@Override
	public FeatureConfig getConfig()
	{
		return this.config;
	}
}
