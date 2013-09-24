package de.thatsich.core.opencv;


/**
 * A Feature Extractor
 * 
 * @author Minh
 *
 */
public abstract class AFeatureExtractor implements IFeatureExtractor {
	
	/**
	 * gets the name of the feature extractor 
	 */
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
