package de.thatsich.openfx.network.intern.control.prediction;

import de.thatsich.core.IEntityConfig;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 09.06.2014.
 */
public class NetworkConfig implements IEntityConfig
{
	public final ReadOnlyStringWrapper date;
	public final ReadOnlyStringWrapper id;

	public NetworkConfig(final String date, final String id)
	{
		this.date = new ReadOnlyStringWrapper(date);
		this.id = new ReadOnlyStringWrapper(id);
	}

	public NetworkConfig(final String fileName)
	{
		final String[] fileNameSplit = fileName.split("_");
		this.date = new ReadOnlyStringWrapper(fileNameSplit[0]);
		this.id = new ReadOnlyStringWrapper(fileNameSplit[1]);
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.date.get());
		joiner.add(this.id.get());

		return joiner.toString();
	}

}
