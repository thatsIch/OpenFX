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
	private final String className;
	private final String extractorName;
	private final int tileSize;
	private final List<IFeatureVector> featureVectors;

	public Feature(final String className, final String extractorName, final int tileSize, final List<IFeatureVector> featureVectors)
	{
		this.className = className;
		this.extractorName = extractorName;
		this.tileSize = tileSize;
		this.featureVectors = featureVectors;
	}

	@Override
	public String extractorName()
	{
		return this.extractorName;
	}

	@Override
	public String className()
	{
		return this.className;
	}

	@Override
	public int tileSize()
	{
		return this.tileSize;
	}

	@Override
	public List<IFeatureVector> vectors()
	{
		return this.featureVectors;
	}
}
