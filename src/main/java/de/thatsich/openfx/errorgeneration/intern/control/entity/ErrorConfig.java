package de.thatsich.openfx.errorgeneration.intern.control.entity;

import de.thatsich.core.IEntityConfiguration;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 05.06.2014.
 */
public class ErrorConfig implements IEntityConfiguration
{
	public final ReadOnlyStringProperty dateTime;
	public final ReadOnlyStringProperty clazz;
	public final ReadOnlyStringProperty id;

	public ErrorConfig(final String dateTime, final String clazz, final String id)
	{
		this.dateTime = new ReadOnlyStringWrapper(dateTime);
		this.clazz = new ReadOnlyStringWrapper(clazz);
		this.id = new ReadOnlyStringWrapper(id);
	}

	public ErrorConfig(final String fileName)
	{
		final String splitString[] = fileName.split("_");

		this.dateTime = new ReadOnlyStringWrapper(splitString[0]);
		this.clazz = new ReadOnlyStringWrapper(splitString[1]);
		this.id = new ReadOnlyStringWrapper(splitString[2]);
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.dateTime.get());
		joiner.add(this.clazz.get());
		joiner.add(this.id.get());

		return joiner.toString();
	}
}
