package de.thatsich.bachelor.javafx.business.model;

import java.nio.file.Path;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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
	final private ListProperty<ImageEntry> imageEntryList = new SimpleListProperty<ImageEntry>(FXCollections.<ImageEntry>observableArrayList());

	// ==================================================
	// Property Implementation
	// ==================================================
	public ListProperty<ImageEntry> getImageEntryListProperty() { return this.imageEntryList; }
	public ObjectProperty<ImageEntry> getSelectedImageEntryProperty() { return this.selectedImageEntry; }	
	public ObjectProperty<Path> getImageInputFolderPathProperty() { return this.imageInputFolderPath; }
}
