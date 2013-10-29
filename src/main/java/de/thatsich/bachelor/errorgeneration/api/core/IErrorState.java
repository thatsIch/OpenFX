package de.thatsich.bachelor.errorgeneration.api.core;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorState {
	public ObjectProperty<Path> getErrorEntryFolderPathProperty();
	public IntegerProperty getErrorLoopCountProperty();
	public Path getErrorEntryFolderPath();
	public int getErrorLoopCount();
	public void setErrorEntryFolderPath(Path errorEntryFolderPath);
	public void setErrorLoopCount(int errorLoopCount);
}
