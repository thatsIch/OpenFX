package de.thatsich.bachelor.classification.intern.model;

import java.nio.file.Path;

import de.thatsich.bachelor.classification.api.core.IClassificationState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ClassificationState implements IClassificationState {
	// Properties
	private final ObjectProperty<Path> binaryClassifierFolderPath = new SimpleObjectProperty<Path>();
	
	// Property Getter
	public ObjectProperty<Path> getBinaryClassifierFolderPathProperty() { return this.binaryClassifierFolderPath; }
	
	// Getter
	public Path getBinaryClassifierFolderPath() { return this.binaryClassifierFolderPath.get(); }
	
	// Setter
	public void setBinaryClassifierFolderPath(Path binaryClassifierFolderPath) { this.binaryClassifierFolderPath.set(binaryClassifierFolderPath); } 
}
