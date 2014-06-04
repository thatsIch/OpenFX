package de.thatsich.openfx.network.api.model;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 31.05.2014.
 */
public interface INetworkState
{
	ObjectProperty<Path> path();
}
