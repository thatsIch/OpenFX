package de.thatsich.bachelor.imageprocessing.intern.model;

import de.thatsich.bachelor.imageprocessing.api.core.IImageState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class ImageState implements IImageState
{

	// Properties
	final private ObjectProperty<Path> imageInputFolderPath = new SimpleObjectProperty<>();
	private final ObjectProperty<Path> lastLocation = new SimpleObjectProperty<>();

	// Property Getter
	public ObjectProperty<Path> imageFolderPathProperty()
	{
		return this.imageInputFolderPath;
	}

	public ObjectProperty<Path> getLastLocationProperty()
	{
		return this.lastLocation;
	}

	// Getter
	public Path getImageFolderPath()
	{
		return this.imageInputFolderPath.get();
	}

	// Setter
	public void setImageFolderPath(Path imageFolderPath)
	{
		this.imageInputFolderPath.set(imageFolderPath);
	}

	public Path getLastLocation()
	{
		return this.lastLocation.get();
	}

	public void setLastLocation(Path lastLocation)
	{
		this.lastLocation.set(lastLocation);
	}
}
