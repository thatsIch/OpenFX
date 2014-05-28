package de.thatsich.bachelor.featureextraction.intern.command.extractor;

import de.thatsich.core.Log;


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
