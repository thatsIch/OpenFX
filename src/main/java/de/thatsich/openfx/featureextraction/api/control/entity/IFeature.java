package de.thatsich.openfx.featureextraction.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureConfig;

import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeature extends IEntity
{
	String extractorName();

	String className();

	int tileSize();

	List<IFeatureVector> vectors();

	@Override
	FeatureConfig getConfig();
}
