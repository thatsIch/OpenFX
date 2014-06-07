package de.thatsich.openfx.errorgeneration.intern.model;

import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class ErrorState implements IErrorState
{
	// Properties
	final private ObjectProperty<Path> errorEntryFolderPath = new SimpleObjectProperty<>();
	final private IntegerProperty errorLoopCount = new SimpleIntegerProperty();

	// Property Getter
	@Override
	public ObjectProperty<Path> path()
	{
		return this.errorEntryFolderPath;
	}

	@Override
	public IntegerProperty loopCount()
	{
		return this.errorLoopCount;
	}

}
