package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core;

import de.thatsich.core.IEntityConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 06.06.2014.
 */
public class PreProcessingConfig implements IEntityConfig
{
	public final ReadOnlyStringProperty name;
	public final ReadOnlyIntegerProperty inputSize;
	public final ReadOnlyIntegerProperty outputSize;
	public final ReadOnlyStringProperty id;

	public PreProcessingConfig(String name, int inputSize, int outputSize, String id)
	{
		this.name = new ReadOnlyStringWrapper(name);
		this.inputSize = new ReadOnlyIntegerWrapper(inputSize);
		this.outputSize = new ReadOnlyIntegerWrapper(outputSize);
		this.id = new ReadOnlyStringWrapper(id);
	}

	public PreProcessingConfig(String fileName)
	{
		final String splitString[] = fileName.split("_");

		this.name = new ReadOnlyStringWrapper(splitString[0]);
		this.inputSize = new ReadOnlyIntegerWrapper(Integer.parseInt(splitString[1]));
		this.outputSize = new ReadOnlyIntegerWrapper(Integer.parseInt(splitString[2]));
		this.id = new ReadOnlyStringWrapper(splitString[3]);
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.name.get());
		joiner.add(String.valueOf(this.inputSize.get()));
		joiner.add(String.valueOf(this.outputSize.get()));
		joiner.add(this.id.get());

		return joiner.toString();
	}
}
