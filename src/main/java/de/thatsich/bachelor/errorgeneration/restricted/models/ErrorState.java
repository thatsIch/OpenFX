package de.thatsich.bachelor.errorgeneration.restricted.models;
	
import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ErrorState {
	
	// Properties
	final private ObjectProperty<Path> errorEntryFolderPath = new SimpleObjectProperty<Path>();
	final private IntegerProperty errorLoopCount = new SimpleIntegerProperty();
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<Path> getErrorEntryFolderPath() { return this.errorEntryFolderPath; }
	public IntegerProperty getErrorLoopCountProperty() { return this.errorLoopCount; }
}
