package de.thatsich.openfx.featureextraction.api.control.entity;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeatureVector
{
	ReadOnlyListProperty<Double> vector();

	ReadOnlyBooleanProperty isPositive();
}
