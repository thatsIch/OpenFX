package de.thatsich.bachelor.imageprocessing.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;

public interface IImageEntries {
	public ListProperty<ImageEntry> imageEntriesmageEntryListProperty();
	public ObjectProperty<ImageEntry> selectedImageEntryProperty();	
	public ImageEntry getSelectedImageEntry();
	public void setSelctedImageEntry(ImageEntry selectedImageEntry);

}
