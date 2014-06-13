package de.thatsich.openfx.preprocessed.api.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public interface IPreProcesseds
{
	/**
	 * PropertyGetter of PreProcessing List
	 *
	 * @return ListProperty of PreProcessings
	 */
	ListProperty<IFeature> list();

	/**
	 * PropertyGetter of selected PreProcessing
	 *
	 * @return Property of selected PreProcessing
	 */
	ObjectProperty<IFeature> selected();
}
