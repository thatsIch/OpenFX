package de.thatsich.openfx.featureextraction.api.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author thatsIch
 * @since 06.06.2014.
 */
public interface IVectors
{
	ListProperty<IFeatureVector> get();

	ObjectProperty<IFeatureVector> selected();
}
