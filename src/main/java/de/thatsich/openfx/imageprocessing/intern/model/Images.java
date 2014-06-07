package de.thatsich.openfx.imageprocessing.intern.model;

import com.google.inject.Singleton;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

@Singleton
public class Images implements IImages
{
	// Properties
	final private ObjectProperty<IImage> selected = new SimpleObjectProperty<>();
	final private ListProperty<IImage> list = new SimpleListProperty<>(FXCollections.<IImage>observableArrayList());

	// Property Getter
	@Override
	public ListProperty<IImage> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IImage> selected()
	{
		return this.selected;
	}
}
