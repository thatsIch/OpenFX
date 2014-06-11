package de.thatsich.openfx.preprocessing.intern.model;

import com.google.inject.Singleton;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
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
	private final ListProperty<ITrainedPreProcessor> list = new SimpleListProperty<>(FXCollections.<ITrainedPreProcessor>observableArrayList());
	private final ObjectProperty<ITrainedPreProcessor> selected = new SimpleObjectProperty<>();

	@Override
	public ListProperty<ITrainedPreProcessor> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<ITrainedPreProcessor> selected()
	{
		return this.selected;
	}
}
