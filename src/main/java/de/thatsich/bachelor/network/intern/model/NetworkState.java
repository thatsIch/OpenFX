package de.thatsich.bachelor.network.intern.model;

import de.thatsich.bachelor.network.api.core.INetworkState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkState implements INetworkState
{
	private final ObjectProperty<Path> path = new SimpleObjectProperty<>();

	@Override
	public ObjectProperty<Path> getPathProperty()
	{
		return this.path;
	}

	@Override
	public Path getNetworkPath()
	{
		return this.path.get();
	}

	@Override
	public void setNetworkPath(final Path networkInputPath)
	{
		this.path.set(networkInputPath);
	}
}
