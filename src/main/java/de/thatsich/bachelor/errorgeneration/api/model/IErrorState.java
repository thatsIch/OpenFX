package de.thatsich.bachelor.errorgeneration.api.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IErrorState
{
	public ObjectProperty<Path> getErrorEntryFolderPathProperty();

	public IntegerProperty getErrorLoopCountProperty();

	public Path getErrorEntryFolderPath();

	public void setErrorEntryFolderPath(Path errorEntryFolderPath);

	public int getErrorLoopCount();

	public void setErrorLoopCount(int errorLoopCount);
}
