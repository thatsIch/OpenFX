package de.thatsich.bachelor.preprocessing.intern.model;

import com.google.inject.Singleton;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessings;

@Singleton
public class PreProcessings implements IPreProcessings
{
	// Properties
	private final ListProperty<IPreProcessing>		list		= new SimpleListProperty<IPreProcessing>( FXCollections.<IPreProcessing >observableArrayList() );
	private final ObjectProperty<IPreProcessing>	selected	= new SimpleObjectProperty<IPreProcessing>();

	@Override
	public ListProperty<IPreProcessing> getPreProcessingListProperty()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IPreProcessing> getSelectedPreProcessingProperty()
	{
		return this.selected;
	}

	@Override
	public IPreProcessing getSelectedPreProcessing()
	{
		return this.selected.get();
	}

	@Override
	public void setSelectedPreProcessing( IPreProcessing pp )
	{
		this.selected.set( pp );
	}

}
