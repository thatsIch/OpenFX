package de.thatsich.openfx.preprocessed.api.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public interface IPreProcessedVectors
{
	ListProperty<IFeatureVector> list();

	ObjectProperty<IFeatureVector> selected();
}
