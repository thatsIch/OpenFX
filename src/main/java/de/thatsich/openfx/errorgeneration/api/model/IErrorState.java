package de.thatsich.openfx.errorgeneration.api.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IErrorState
{
	public ObjectProperty<Path> path();

	public IntegerProperty loopCount();
}
