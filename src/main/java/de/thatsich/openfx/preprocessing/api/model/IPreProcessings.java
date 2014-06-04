package de.thatsich.openfx.preprocessing.api.model;

import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;


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
	ListProperty<IPreProcessing> list();

	/**
	 * PropertyGetter of selected PreProcessing
	 *
	 * @return Property of selected PreProcessing
	 */
	ObjectProperty<IPreProcessing> selected();

}
