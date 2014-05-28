package de.thatsich.bachelor.imageprocessing.api.core;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IImageState
{
	// Property Getter
	public ObjectProperty<Path> imageFolderPathProperty();

	public ObjectProperty<Path> getLastLocationProperty();

	// Getter
	public Path getImageFolderPath();

	// Setter
	public void setImageFolderPath(Path imageFolderPath);

	public Path getLastLocation();

	public void setLastLocation(Path lastLocation);
}
