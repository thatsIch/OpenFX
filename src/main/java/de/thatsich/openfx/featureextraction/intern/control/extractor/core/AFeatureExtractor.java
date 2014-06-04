package de.thatsich.openfx.featureextraction.intern.control.extractor.core;

import de.thatsich.core.Log;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;


/**
 * A Feature Extractor
 *
 * @author Minh
 */
public abstract class AFeatureExtractor implements IFeatureExtractor
{
	protected Log log;

	/**
	 * gets the name of the feature extractor
	 */
	@Override
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
