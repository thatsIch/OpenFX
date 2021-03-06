package de.thatsich.openfx.prediction.intern.control.entity;

import de.thatsich.core.IEntityConfig;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 15.06.2014.
 */
public class NetworkPredictionConfig implements IEntityConfig
{
	public final ReadOnlyStringProperty dateTime;
	public final ReadOnlyStringProperty predictedClassName;
	public final ReadOnlyStringProperty id;

	public NetworkPredictionConfig(String dateTime, String predictedClassName, String id)
	{
		this.dateTime = new ReadOnlyStringWrapper(dateTime);
		this.predictedClassName = new ReadOnlyStringWrapper(predictedClassName);
		this.id = new ReadOnlyStringWrapper(id);
	}

	public NetworkPredictionConfig(String fileName)
	{
		final String splitString[] = fileName.split("_");
		final int lastSplitIndex = splitString.length - 1;

		final String dateTime = splitString[0];
		final String id = splitString[lastSplitIndex];
		final String prediction = fileName.replaceFirst(dateTime + "_", "").replaceFirst("_" + id, "");

		this.dateTime = new ReadOnlyStringWrapper(dateTime);
		this.predictedClassName = new ReadOnlyStringWrapper(prediction);
		this.id = new ReadOnlyStringWrapper(id);
	}


	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.dateTime.get());
		joiner.add(this.predictedClassName.get());
		joiner.add(this.id.get());

		return joiner.toString();
	}
}
