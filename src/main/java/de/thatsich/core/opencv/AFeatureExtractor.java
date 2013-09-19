package de.thatsich.core.opencv;


/**
 * 
 * @author Minh
 *
 */
public abstract class AFeatureExtractor implements IFeatureExtractor {

	
	/**
	 * 
	 */
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
