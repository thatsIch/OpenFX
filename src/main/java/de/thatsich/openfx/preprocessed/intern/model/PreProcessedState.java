package de.thatsich.openfx.preprocessed.intern.model;

import de.thatsich.openfx.preprocessed.api.model.IPreProcessedState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public class PreProcessedState implements IPreProcessedState
{
	// Properties
	private final ObjectProperty<Path> path = new SimpleObjectProperty<>();

	@Override
	public ObjectProperty<Path> path()
	{
		return this.path;
	}
}
