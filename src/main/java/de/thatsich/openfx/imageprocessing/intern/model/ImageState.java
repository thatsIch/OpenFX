package de.thatsich.openfx.imageprocessing.intern.model;

import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class ImageState implements IImageState
{
	// Properties
	private final ObjectProperty<Path> imageInputFolderPath = new SimpleObjectProperty<>();
	private final ObjectProperty<Path> lastLocation = new SimpleObjectProperty<>();

	// Property Getter
	@Override
	public ObjectProperty<Path> path()
	{
		return this.imageInputFolderPath;
	}

	@Override
	public ObjectProperty<Path> lastLocation()
	{
		return this.lastLocation;
	}
}
