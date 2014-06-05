package de.thatsich.openfx.featureextraction.api.control.entity;

import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeature
{
	String extractorName();

	String className();

	int tileSize();

	List<IFeatureVector> vectors();
}
