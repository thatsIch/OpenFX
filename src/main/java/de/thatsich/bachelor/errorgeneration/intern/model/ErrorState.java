package de.thatsich.bachelor.errorgeneration.intern.model;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorState;
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
	public ObjectProperty<Path> getErrorEntryFolderPathProperty()
	{ return this.errorEntryFolderPath; }

	public IntegerProperty getErrorLoopCountProperty() { return this.errorLoopCount; }

	// Getter
	public Path getErrorEntryFolderPath()
	{ return this.errorEntryFolderPath.get(); }

	// Setter
	public void setErrorEntryFolderPath(Path errorEntryFolderPath)
	{ this.errorEntryFolderPath.set(errorEntryFolderPath); }

	public int getErrorLoopCount() { return this.errorLoopCount.get(); }

	public void setErrorLoopCount(int errorLoopCount) { this.errorLoopCount.set(errorLoopCount); }
}
