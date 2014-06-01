package de.thatsich.bachelor.preprocessing.intern.model;

import com.google.inject.Singleton;
import de.thatsich.bachelor.preprocessing.api.model.IPreProcessors;
import de.thatsich.bachelor.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

@Singleton
public class PreProcessors implements IPreProcessors
{
	// Properties
	private final ListProperty<IPreProcessor> list = new SimpleListProperty<>(FXCollections.<IPreProcessor>observableArrayList());
	private final ObjectProperty<IPreProcessor> selected = new SimpleObjectProperty<>();

	@Override
	public ListProperty<IPreProcessor> getPreProcessorListProperty()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IPreProcessor> getSelectedPreProcessorProperty()
	{
		return this.selected;
	}

	@Override
	public IPreProcessor getSelectedPreProcessor()
	{
		return this.selected.get();
	}

	@Override
	public void setSelectedPreProcessor(IPreProcessor preProcessor)
	{
		this.selected.set(preProcessor);
	}
}
