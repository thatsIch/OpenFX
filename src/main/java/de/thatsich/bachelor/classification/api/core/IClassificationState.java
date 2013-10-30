package de.thatsich.bachelor.classification.api.core;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;

public interface IClassificationState {
	// Property Getter
	public ObjectProperty<Path> getBinaryClassifierFolderPathProperty();
	
	// Getter
	public Path getBinaryClassifierFolderPath();
	
	// Setter
	public void setBinaryClassifierFolderPath(Path binaryClassifierFolderPath); 

}
