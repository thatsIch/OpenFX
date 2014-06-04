package de.thatsich.openfx.prediction.api.model;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IPredictionState
{
	// Property Getter
	public ObjectProperty<Path> path();
}
