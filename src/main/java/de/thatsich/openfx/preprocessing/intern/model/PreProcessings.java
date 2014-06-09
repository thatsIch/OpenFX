package de.thatsich.openfx.preprocessing.intern.model;

import com.google.inject.Singleton;
import de.thatsich.openfx.preprocessing.api.control.entity.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

@Singleton
public class PreProcessings implements IPreProcessings
{
	// Properties
	private final ListProperty<IPreProcessing> list = new SimpleListProperty<>(FXCollections.<IPreProcessing>observableArrayList());
	private final ObjectProperty<IPreProcessing> selected = new SimpleObjectProperty<>();

	@Override
	public ListProperty<IPreProcessing> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IPreProcessing> selected()
	{
		return this.selected;
	}
}
