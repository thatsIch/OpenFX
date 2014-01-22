package de.thatsich.bachelor.preprocessing.intern.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.preprocessing.api.core.IPreProcessors;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessor;


/**
 * Model Class
 * 
 * Contains access to the selected and all PreProcessors stored
 * 
 * @author thatsIch
 */
public class PreProcessors implements IPreProcessors
{
	/**
	 * Property of selected PreProcessor
	 */
	final private ObjectProperty<IPreProcessor>	selectedPreProcessor	= new SimpleObjectProperty<>();

	/**
	 * Property of PreProcessor List
	 */
	final private ListProperty<IPreProcessor>	preProcessorList		= new SimpleListProperty<>( FXCollections.<IPreProcessor >observableArrayList() );

	/**
	 * Property Getter of PreProcessor List
	 * 
	 * @return List of PreProcessor
	 */
	public ListProperty<IPreProcessor> getPreProcessorListProperty()
	{
		return this.preProcessorList;
	}

	/**
	 * Property Getter of selected PreProcessor
	 * 
	 * @return Property of selected PreProcessor
	 */
	public ObjectProperty<IPreProcessor> getSelectedPreProcessorProperty()
	{
		return this.selectedPreProcessor;
	}

	/**
	 * Getter of selected PreProcessor
	 * 
	 * @return selected PreProcessor
	 */
	public IPreProcessor getSelectedPreProcessor()
	{
		return this.selectedPreProcessor.get();
	}

	/**
	 * Setter of selected PreProcessor
	 * 
	 * @param preprocessor selected PreProcessor
	 */
	public void setSelectedPreProcessor( IPreProcessor preprocessor )
	{
		this.selectedPreProcessor.set( preprocessor );
	}
}
