package de.thatsich.bachelor.imageprocessing.restricted.model;

import java.nio.file.Path;

import de.thatsich.bachelor.imageprocessing.api.core.IImageState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ImageState implements IImageState {

	// Properties
	final private ObjectProperty<Path> imageInputFolderPath = new SimpleObjectProperty<Path>();
	
	// Property Getter
	public ObjectProperty<Path> imageFolderPathProperty() { return this.imageInputFolderPath; }
	
	// Getter
	public Path getImageFolderPath() { return this.imageInputFolderPath.get(); }
	
	// Setter
	public void setImageFolderPath(Path imageFolderPath) { this.imageInputFolderPath.set(imageFolderPath); }
}
