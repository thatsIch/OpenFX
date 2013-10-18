package de.thatsich.bachelor.javafx.business.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;

public class ImageEntries {
	
	// Properties
	final private ObjectProperty<ImageEntry> selectedImageEntry = new SimpleObjectProperty<ImageEntry>();
	final private ListProperty<ImageEntry> imageEntryList = new SimpleListProperty<ImageEntry>(FXCollections.<ImageEntry>observableArrayList());

	// Property Getter
	public ListProperty<ImageEntry> getImageEntryListProperty() { return this.imageEntryList; }
	public ObjectProperty<ImageEntry> getSelectedImageEntryProperty() { return this.selectedImageEntry; }	
}
