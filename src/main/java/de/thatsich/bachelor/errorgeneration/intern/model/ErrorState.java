package de.thatsich.bachelor.errorgeneration.intern.model;

import de.thatsich.bachelor.errorgeneration.api.model.IErrorState;
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
	public ObjectProperty<Path> path()
	{
		return this.errorEntryFolderPath;
	}

	public IntegerProperty loopCount()
	{
		return this.errorLoopCount;
	}

}
