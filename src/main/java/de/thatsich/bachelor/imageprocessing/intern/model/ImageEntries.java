package de.thatsich.bachelor.imageprocessing.intern.model;

import com.google.inject.Singleton;
import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

@Singleton
public class ImageEntries implements IImageEntries
{

	// Properties
	final private ObjectProperty<ImageEntry> selectedImageEntry = new SimpleObjectProperty<>();
	final private ListProperty<ImageEntry> imageEntryList = new SimpleListProperty<>(FXCollections.<ImageEntry>observableArrayList());

	// Property Getter
	public ListProperty<ImageEntry> imageEntriesmageEntryListProperty()
	{ return this.imageEntryList; }

	public ObjectProperty<ImageEntry> selectedImageEntryProperty() { return this.selectedImageEntry; }

	// Getter
	public ImageEntry getSelectedImageEntry()
	{ return this.selectedImageEntry.get(); }

	// Setter
	public void setSelctedImageEntry(ImageEntry selectedImageEntry)
	{ this.selectedImageEntry.set(selectedImageEntry); }
}
