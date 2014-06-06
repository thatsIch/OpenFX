package de.thatsich.openfx.preprocessing.api.model;

import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;


/**
 * Interface for PreProcessor Model
 *
 * Enables access to the PreProcessor List Property and
 * has Getter, Setter and Property for the currently selected one
 *
 * @author thatsIch
 */
public interface IPreProcessors
{
	/**
	 * PropertyGetter of PreProcessor List
	 *
	 * @return ListProperty of PreProcessors
	 */
	ListProperty<IPreProcessor> get();

	/**
	 * PropertyGetter of selected PreProcessor
	 *
	 * @return Property of selected PreProcessor
	 */
	ObjectProperty<IPreProcessor> selected();
}
