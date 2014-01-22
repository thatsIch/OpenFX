package de.thatsich.bachelor.preprocessing.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessor;


/**
 * Model Interface
 * 
 * Grants Access to whole PreProcessorList and selected one
 * by Property. Selected can be get/set directly
 * 
 * @author thatsIch
 */
public interface IPreProcessors
{
	/**
	 * Property Getter of PreProcessor List
	 * 
	 * @return List of PreProcessor
	 */
	ListProperty<IPreProcessor> getPreProcessorListProperty();

	/**
	 * Property Getter of selected PreProcessor
	 * 
	 * @return Property of selected PreProcessor
	 */
	ObjectProperty<IPreProcessor> getSelectedPreProcessorProperty();

	/**
	 * Getter of selected PreProcessor
	 * 
	 * @return selected PreProcessor
	 */
	IPreProcessor getSelectedPreProcessor();

	/**
	 * Setter of selected PreProcessor
	 * 
	 * @param preprocessor
	 *            selected PreProcessor
	 */
	void setSelectedPreProcessor( IPreProcessor preprocessor );
}
