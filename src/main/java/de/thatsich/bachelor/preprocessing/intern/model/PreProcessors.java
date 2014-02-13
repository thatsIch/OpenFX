package de.thatsich.bachelor.preprocessing.intern.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessors;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;


public class PreProcessors implements IPreProcessors
{
	// Properties
	private final ListProperty<IPreProcessor>	list		= new SimpleListProperty<IPreProcessor>( FXCollections.<IPreProcessor >observableArrayList() );
	private final ObjectProperty<IPreProcessor>	selected	= new SimpleObjectProperty<IPreProcessor>();

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
	public void setSelectedPreProcessor( IPreProcessor preProcessor )
	{
		this.selected.set( preProcessor );
	}
}
