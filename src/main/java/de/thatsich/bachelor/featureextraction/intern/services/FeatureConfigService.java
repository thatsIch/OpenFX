package de.thatsich.bachelor.featureextraction.intern.services;

import de.thatsich.core.AConfigurationService;


/**
 * 
 * @author Tran Minh Do
 *
 */
public class FeatureConfigService extends AConfigurationService {

	/**
	 * 
	 */
	private static final String LAST_FRAME_SIZE = "last_frame_size";
	private static final String LAST_FEATURE_EXTRACTOR_INDEX = "last_feature_extractor";
	private static final String LAST_FEATURE_VECTOR_INDEX = "last_feature_vector_index";
	
	/**
	 * 
	 */
	private static final int DEFAULT_LAST_FRAME_SIZE = 2;
	private static final int DEFAULT_LAST_FEATURE_EXTRACTOR_INDEX = 0;
	private static final int DEFAULT_LAST_FEATURE_VECTOR_INDEX = 0;
	
	// ================================================== 
	// Getter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public int getLastFrameSizeInt() {
		return super.get(LAST_FRAME_SIZE, DEFAULT_LAST_FRAME_SIZE);
	}
	
	/**
	 * 
	 */
	public int getLastFeatureExtractorIndexInt() {
		return super.get(LAST_FEATURE_EXTRACTOR_INDEX, DEFAULT_LAST_FEATURE_EXTRACTOR_INDEX);
	}
	
	/**
	 * 
	 */
	public int getLastFeatureVectorIndexInt() {
		return super.get(LAST_FEATURE_VECTOR_INDEX, DEFAULT_LAST_FEATURE_VECTOR_INDEX);
	}

	// ================================================== 
	// Setter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public void setLastFrameSizeInt(int lastFrameSize) {
		super.set(LAST_FRAME_SIZE, lastFrameSize);
	}
	
	/**
	 * 
	 */
	public void setLastFeatureExtractorIndexInt(int lastFeatureExtractorIndex) {
		super.set(LAST_FEATURE_EXTRACTOR_INDEX, lastFeatureExtractorIndex);
	}
	
	/**
	 * 
	 */
	public void setLastFeatureVectorIndexInt(int lastFeatureVectorIndex) {
		super.set(LAST_FEATURE_VECTOR_INDEX, lastFeatureVectorIndex);
	}
}
