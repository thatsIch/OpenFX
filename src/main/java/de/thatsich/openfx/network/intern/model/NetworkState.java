package de.thatsich.openfx.network.intern.model;

import de.thatsich.openfx.network.api.model.INetworkState;
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
	public ObjectProperty<Path> path()
	{
		return this.path;
	}
}
