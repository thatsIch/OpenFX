package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.core.IEntityConfiguration;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 05.06.2014.
 */
public class FeatureConfig implements IEntityConfiguration
{
	public final String className;
	public final String extractorName;
	public final int tileSize;

	public FeatureConfig(final String className, final String extractorName, final int tileSize)
	{
		this.className = className;
		this.extractorName = extractorName;
		this.tileSize = tileSize;
	}

	public FeatureConfig(final String fileName)
	{
		final String[] fileNameSplit = fileName.split("_");
		this.className = fileNameSplit[0];
		this.extractorName = fileNameSplit[1];
		this.tileSize = Integer.parseInt(fileNameSplit[2]);
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_", "", ".csv");
		joiner.add(this.className);
		joiner.add(this.extractorName);
		joiner.add(String.valueOf(this.tileSize));

		return joiner.toString();
	}
}
