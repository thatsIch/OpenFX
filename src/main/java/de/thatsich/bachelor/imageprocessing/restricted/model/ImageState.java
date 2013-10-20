package de.thatsich.bachelor.imageprocessing.restricted.model;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ImageState {

	// Properties
	final private ObjectProperty<Path> imageInputFolderPath = new SimpleObjectProperty<Path>();
	
	// Property Getter
	public ObjectProperty<Path> getImageInputFolderPathProperty() { return this.imageInputFolderPath; }
}
