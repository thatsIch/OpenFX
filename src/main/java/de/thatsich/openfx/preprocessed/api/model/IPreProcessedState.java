package de.thatsich.openfx.preprocessed.api.model;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public interface IPreProcessedState
{
	ObjectProperty<Path> path();
}
