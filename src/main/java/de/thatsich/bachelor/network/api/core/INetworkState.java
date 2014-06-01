package de.thatsich.bachelor.network.api.core;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 31.05.2014.
 */
public interface INetworkState
{
	ObjectProperty<Path> getPathProperty();
	Path getNetworkPath();
	void setNetworkPath(Path networkInputPath);
}
