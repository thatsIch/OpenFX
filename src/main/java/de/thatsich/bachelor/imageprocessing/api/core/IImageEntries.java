package de.thatsich.bachelor.imageprocessing.api.core;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IImageEntries
{
	public ListProperty<ImageEntry> imageEntriesmageEntryListProperty();

	public ObjectProperty<ImageEntry> selectedImageEntryProperty();

	public ImageEntry getSelectedImageEntry();

	public void setSelctedImageEntry(ImageEntry selectedImageEntry);

}
