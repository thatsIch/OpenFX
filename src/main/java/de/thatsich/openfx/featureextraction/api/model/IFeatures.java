package de.thatsich.openfx.featureextraction.api.model;

import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeatures
{
	ListProperty<Feature> list();

	ObjectProperty<Feature> selected();
}
