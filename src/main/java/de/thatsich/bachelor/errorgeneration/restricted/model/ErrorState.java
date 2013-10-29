package de.thatsich.bachelor.errorgeneration.restricted.model;
	
import java.nio.file.Path;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ErrorState implements IErrorState {
	
	// Properties
	final private ObjectProperty<Path> errorEntryFolderPath = new SimpleObjectProperty<Path>();
	final private IntegerProperty errorLoopCount = new SimpleIntegerProperty();
	
	// Property Getter
	public ObjectProperty<Path> getErrorEntryFolderPath() { return this.errorEntryFolderPath; }
	public IntegerProperty getErrorLoopCountProperty() { return this.errorLoopCount; }
}
