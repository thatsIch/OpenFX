package de.thatsich.openfx.prediction.intern.model;

import de.thatsich.openfx.prediction.api.model.IPredictionState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class PredictionState implements IPredictionState
{
	// Properties
	private final ObjectProperty<Path> path = new SimpleObjectProperty<>();

	@Override
	public ObjectProperty<Path> path()
	{
		return this.path;
	}
}
