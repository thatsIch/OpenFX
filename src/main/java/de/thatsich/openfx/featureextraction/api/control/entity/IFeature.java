package de.thatsich.openfx.featureextraction.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyStringProperty;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeature extends IEntity
{
	ReadOnlyStringProperty extractorName();

	ReadOnlyStringProperty className();

	ReadOnlyIntegerProperty tileSize();

	ReadOnlyListProperty<IFeatureVector> vectors();

	@Override
	FeatureConfig getConfig();
}
