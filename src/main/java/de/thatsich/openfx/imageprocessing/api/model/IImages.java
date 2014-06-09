package de.thatsich.openfx.imageprocessing.api.model;

import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IImages
{
	ListProperty<IImage> list();

	ObjectProperty<IImage> selected();
}
