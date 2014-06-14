package de.thatsich.openfx.network.intern.control.prediction;

import de.thatsich.core.IEntityConfig;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 09.06.2014.
 */
public class NetworkConfig implements IEntityConfig
{
	public final ReadOnlyStringProperty date;
	public final ReadOnlyStringProperty id;
	public final ReadOnlyLongProperty trainTime;

	public NetworkConfig(final String date, final String id, final long trainTime)
	{
		this.date = new ReadOnlyStringWrapper(date);
		this.id = new ReadOnlyStringWrapper(id);
		this.trainTime = new ReadOnlyLongWrapper(trainTime);
	}

	public NetworkConfig(final String fileName)
	{
		final String[] fileNameSplit = fileName.split("_");
		this.date = new ReadOnlyStringWrapper(fileNameSplit[0]);
		this.id = new ReadOnlyStringWrapper(fileNameSplit[1]);
		this.trainTime = new ReadOnlyLongWrapper(Long.parseLong(fileNameSplit[2]));
	}

	@Override
	public String toString()
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(this.date.get());
		joiner.add(this.id.get());
		joiner.add(String.valueOf(this.trainTime.get()));

		return joiner.toString();
	}

}
