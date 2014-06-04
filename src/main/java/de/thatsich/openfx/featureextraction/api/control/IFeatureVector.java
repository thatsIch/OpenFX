package de.thatsich.openfx.featureextraction.api.control;

import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeatureVector
{
	List<Float> vector();

	boolean isPositive();
}
