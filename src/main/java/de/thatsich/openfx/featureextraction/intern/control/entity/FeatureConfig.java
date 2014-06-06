package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.core.IEntityConfiguration;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 05.06.2014.
 */
public class FeatureConfig implements IEntityConfiguration
{
	public final ReadOnlyStringWrapper className;
	public final ReadOnlyStringWrapper extractorName;
	public final ReadOnlyIntegerWrapper tileSize;

	public FeatureConfig(final String className, final String extractorName, final int tileSize)
	{
		this.className = new ReadOnlyStringWrapper(className);
		this.extractorName = new ReadOnlyStringWrapper(extractorName);
		this.tileSize = new ReadOnlyIntegerWrapper(tileSize);
	}

	public FeatureConfig(final String fileName)
	{
		final String[] fileNameSplit = fileName.split("_");
		this.className = new ReadOnlyStringWrapper(fileNameSplit[0]);
		this.extractorName = new ReadOnlyStringWrapper(fileNameSplit[1]);
		this.tileSize = new ReadOnlyIntegerWrapper(Integer.parseInt(fileNameSplit[2]));
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_", "", ".csv");
		joiner.add(this.className.get());
		joiner.add(this.extractorName.get());
		joiner.add(String.valueOf(this.tileSize.get()));

		return joiner.toString();
	}
}
