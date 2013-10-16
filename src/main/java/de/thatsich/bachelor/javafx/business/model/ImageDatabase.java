package de.thatsich.bachelor.javafx.business.model;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;

/**
 * Model class for storing the images
 * and a simple interface importing
 * images through Path Objects
 * 
 * @author Minh
 *
 */
public class ImageDatabase {
	
	// properties
	final private ObjectProperty<Path> imageInputFolderPath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<ImageEntry> selectedImageEntry = new SimpleObjectProperty<ImageEntry>();
	final private ObjectProperty<ObservableList<ImageEntry>> imageEntryList = new ChoiceBox<ImageEntry>().itemsProperty();

	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<ImageEntry>> getImageEntryListProperty() { return this.imageEntryList; }
	public ObjectProperty<ImageEntry> getSelectedImageEntryProperty() { return this.selectedImageEntry; }	
	public ObjectProperty<Path> getImageInputFolderPathProperty() { return this.imageInputFolderPath; }
}
