package de.thatsich.openfx.imageprocessing.api.model;

import de.thatsich.openfx.imageprocessing.api.control.ImageEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IImageEntries
{
	public ListProperty<ImageEntry> imageEntryListProperty();

	public ObjectProperty<ImageEntry> selectedImageEntryProperty();

	public ImageEntry getSelectedImageEntry();

	public void setSelectedImageEntry(ImageEntry selectedImageEntry);

}
