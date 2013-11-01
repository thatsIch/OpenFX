package de.thatsich.bachelor.imageprocessing.api.core;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;

public interface IImageState {
	// Property Getter
	public ObjectProperty<Path> imageFolderPathProperty();
	
	// Getter
	public Path getImageFolderPath();
	
	// Setter
	public void setImageFolderPath(Path imageFolderPath);
}
