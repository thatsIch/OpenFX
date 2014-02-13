package de.thatsich.bachelor.preprocessing.api.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;


/**
 * Interface for PreProcessings Model.
 * 
 * Enables access to the PreProcessing List Property and
 * has Getter, Setter and Property for the currently selected one
 * 
 * @author thatsIch
 */
public interface IPreProcessings
{
	/**
	 * PropertyGetter of PreProcessing List
	 * 
	 * @return ListProperty of PreProcessings
	 */
	ListProperty<IPreProcessing> getPreProcessingListProperty();

	/**
	 * PropertyGetter of selected PreProcessing
	 * 
	 * @return Property of selected PreProcessing
	 */
	ObjectProperty<IPreProcessing> getSelectedPreProcessingProperty();

	/**
	 * Getter of selected Binary Classification
	 * 
	 * @return Selected Binary Classification
	 */
	IPreProcessing getSelectedPreProcessing();

	/**
	 * Setter of selected PreProcessing
	 * 
	 * @param bc
	 *            Selected PreProcessing
	 */
	void setSelectedPreProcessing( IPreProcessing pp );

}
