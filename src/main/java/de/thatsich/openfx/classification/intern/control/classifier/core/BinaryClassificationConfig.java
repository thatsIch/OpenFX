package de.thatsich.openfx.classification.intern.control.classifier.core;

import de.thatsich.core.IEntityConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.StringJoiner;

public class BinaryClassificationConfig implements IEntityConfig
{
	// Properties
	public final ReadOnlyStringProperty classificationName;
	public final ReadOnlyStringProperty extractorName;
	public final ReadOnlyIntegerProperty tileSize;
	public final ReadOnlyStringProperty errorName;
	public final ReadOnlyStringProperty id;
	public final ReadOnlyLongProperty trainTime;

	public BinaryClassificationConfig(String classificationName, String extractorName, int tileSize, String errorName, String id, long trainTime)
	{
		this.classificationName = new SimpleStringProperty(classificationName);
		this.extractorName = new SimpleStringProperty(extractorName);
		this.tileSize = new SimpleIntegerProperty(tileSize);
		this.errorName = new SimpleStringProperty(errorName);
		this.id = new SimpleStringProperty(id);
		this.trainTime = new SimpleLongProperty(trainTime);
	}

	public BinaryClassificationConfig(String fileName)
	{
		final String split[] = fileName.split("_");

		this.classificationName = new SimpleStringProperty(split[0]);
		this.extractorName = new SimpleStringProperty(split[1]);
		this.tileSize = new SimpleIntegerProperty(Integer.parseInt(split[2]));
		this.errorName = new SimpleStringProperty(split[3]);
		this.id = new SimpleStringProperty(split[4]);
		this.trainTime = new SimpleLongProperty(Long.parseLong(split[5]));
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.classificationName.get());
		joiner.add(this.extractorName.get());
		joiner.add(String.valueOf(this.tileSize.get()));
		joiner.add(this.errorName.get());
		joiner.add(this.id.get());
		joiner.add(String.valueOf(this.trainTime.get()));

		return joiner.toString();
	}
}
