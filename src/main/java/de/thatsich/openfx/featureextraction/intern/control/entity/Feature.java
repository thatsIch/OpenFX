package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.IFeature;

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
	private final List<FeatureVector> featureVectors;

	public Feature(final Path path, final String className, final String extractorName, final int frameSize, final List<FeatureVector> featureVectors)
	{
		this.path = path;
		this.className = className;
		this.extractorName = extractorName;
		this.frameSize = frameSize;
		this.featureVectors = featureVectors;
	}

	@Override
	public Path getPath()
	{
		return this.path;
	}

	@Override
	public String getExtractorName()
	{
		return this.extractorName;
	}

	@Override
	public String getClassName()
	{
		return this.className;
	}

	@Override
	public int getFrameSize()
	{
		return this.frameSize;
	}

	@Override
	public List<FeatureVector> getFeatureVectors()
	{
		return this.featureVectors;
	}
}
