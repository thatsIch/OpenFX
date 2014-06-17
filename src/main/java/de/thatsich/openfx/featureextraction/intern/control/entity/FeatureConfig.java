package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.core.IEntityConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 05.06.2014.
 */
public class FeatureConfig implements IEntityConfig
{
	public final ReadOnlyStringProperty className;
	public final ReadOnlyStringProperty extractorName;
	public final ReadOnlyStringProperty preProcessorName;
	public final ReadOnlyIntegerProperty tileSize;
	public final ReadOnlyLongWrapper trainTime;

	public FeatureConfig(final String className, final String extractorName, final String preProcessorName, final int tileSize, final long trainTime)
	{
		this.className = new SimpleStringProperty(className);
		this.extractorName = new SimpleStringProperty(extractorName);
		this.preProcessorName = new SimpleStringProperty(preProcessorName);
		this.tileSize = new SimpleIntegerProperty(tileSize);
		this.trainTime = new ReadOnlyLongWrapper(trainTime);
	}

	public FeatureConfig(final String fileName)
	{
		final String[] fileNameSplit = fileName.split("_");

		this.className = new SimpleStringProperty(fileNameSplit[0]);
		this.extractorName = new SimpleStringProperty(fileNameSplit[1]);
		this.preProcessorName = new SimpleStringProperty(fileNameSplit[2]);
		this.tileSize = new SimpleIntegerProperty(Integer.parseInt(fileNameSplit[3]));
		this.trainTime = new ReadOnlyLongWrapper(Long.parseLong(fileNameSplit[4]));
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.className.get());
		joiner.add(this.extractorName.get());
		joiner.add(this.preProcessorName.get());
		joiner.add(String.valueOf(this.tileSize.get()));
		joiner.add(String.valueOf(this.trainTime.get()));

		return joiner.toString();
	}
}
