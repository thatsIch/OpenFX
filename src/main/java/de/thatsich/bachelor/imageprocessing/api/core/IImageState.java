package de.thatsich.bachelor.imageprocessing.api.core;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;

public interface IImageState {
	// Property Getter
	public ObjectProperty<Path> imageFolderPathProperty();
	public ObjectProperty<Path> getLastLocationProperty();
	
	// Getter
	public Path getImageFolderPath();
	public Path getLastLocation();
	
	// Setter
	public void setImageFolderPath(Path imageFolderPath);
	public void setLastLocation(Path lastLocation);
}
