package de.thatsich.openfx.featureextraction.api.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeatures
{
	ListProperty<IFeature> list();

	ObjectProperty<IFeature> selectedFeature();

	ObjectProperty<IFeatureVector> selectedVector();

	IntegerProperty index();
}
