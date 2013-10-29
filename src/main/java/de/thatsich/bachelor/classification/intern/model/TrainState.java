package de.thatsich.bachelor.classification.intern.model;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TrainState {
	// Properties
	private final ObjectProperty<Path> binaryClassifierFolderPath = new SimpleObjectProperty<Path>();
	
	// Property Getter
	public ObjectProperty<Path> getBinaryClassifierFolderPathProperty() { return this.binaryClassifierFolderPath; }
}
