package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;

import java.nio.file.Path;
import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public class Feature implements IFeature
{
	// Properties
	private final Path path;
	private final String className;
	private final String extractorName;
	private final int frameSize;
	private final List<IFeatureVector> featureVectors;

	public Feature(final Path path, final String className, final String extractorName, final int frameSize, final List<IFeatureVector> featureVectors)
	{
		this.path = path;
		this.className = className;
		this.extractorName = extractorName;
		this.frameSize = frameSize;
		this.featureVectors = featureVectors;
	}

	@Override
	public Path path()
	{
		return this.path;
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
	public int frameSize()
	{
		return this.frameSize;
	}

	@Override
	public List<IFeatureVector> vectors()
	{
		return this.featureVectors;
	}
}
