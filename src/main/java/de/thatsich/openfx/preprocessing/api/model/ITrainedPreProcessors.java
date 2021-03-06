package de.thatsich.openfx.preprocessing.api.model;

import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
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
public interface ITrainedPreProcessors
{
	/**
	 * PropertyGetter of PreProcessing List
	 *
	 * @return ListProperty of PreProcessings
	 */
	ListProperty<ITrainedPreProcessor> list();

	/**
	 * PropertyGetter of selected PreProcessing
	 *
	 * @return Property of selected PreProcessing
	 */
	ObjectProperty<ITrainedPreProcessor> selected();

}
