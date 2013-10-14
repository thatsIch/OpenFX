package de.thatsich.bachelor.service;

import de.thatsich.core.AConfiguration;


/**
 * 
 * @author Tran Minh Do
 *
 */
public class ConfigService extends AConfiguration {

	/**
	 * 
	 */
	private static final String LAST_LOCATION = "last_location";
	private static final String LAST_IMAGE = "last_image";
	private static final String LAST_ERROR = "last_error";
	private static final String LAST_ERROR_GENERATOR = "last_error_generator";
	private static final String LAST_ERROR_COUNT = "last_error_count";
	private static final String LAST_FRAME_SIZE = "last_frame_size";
	private static final String LAST_FEATURE_EXTRACTOR_INDEX = "last_feature_extractor";
	private static final String LAST_FEATURE_VECTOR_INDEX = "last_feature_vector_index";
	
	/**
	 * 
	 */
	private static final String DEFAULT_LAST_LOCATION = System.getProperty("user.home");
	private static final int DEFAULT_LAST_IMAGE = 0;
	private static final int DEFAULT_LAST_ERROR = 0;
	private static final int DEFAULT_LAST_ERROR_GENERATOR = 0;
	private static final int DEFAULT_LAST_ERROR_COUNT = 1;
	private static final int DEFAULT_LAST_FRAME_SIZE = 2;
	private static final int DEFAULT_LAST_FEATURE_EXTRACTOR_INDEX = 0;
	private static final int DEFAULT_LAST_FEATURE_VECTOR_INDEX = 0;
	
	// ================================================== 
	// Getter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public String getLastLocationString() {
		return super.get(LAST_LOCATION, DEFAULT_LAST_LOCATION);
	}
	
	/**
	 * 
	 */
	public int getLastImageIndexInt() {
		return super.get(LAST_IMAGE, DEFAULT_LAST_IMAGE);
	}
	
	/**
	 * 
	 */
	public int getLastErrorIndexInt() {
		return super.get(LAST_ERROR, DEFAULT_LAST_ERROR);
	}
	
	/**
	 * 
	 */
	public int getLastErrorGeneratorIndexInt() {
		return super.get(LAST_ERROR_GENERATOR, DEFAULT_LAST_ERROR_GENERATOR);
	}
	
	/**
	 * 
	 */
	public int getLastErrorCountInt() {
		return super.get(LAST_ERROR_COUNT, DEFAULT_LAST_ERROR_COUNT);
	}
	
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
	public void setLastLocationString(String lastLocation) {
		super.set(LAST_LOCATION, lastLocation);
	}

	/**
	 * 
	 */
	public void setLastImageIndexInt(int lastImageIndex) {
		super.set(LAST_IMAGE, lastImageIndex);
	}

	/**
	 * 
	 */
	public void setLastErrorIndexInt(int lastErrorIndex) {
		super.set(LAST_ERROR, lastErrorIndex);
	}

	/**
	 * 
	 */
	public void setLastErrorGeneratorIndexInt(int lastErrorGeneratorIndex) {
		super.set(LAST_ERROR_GENERATOR, lastErrorGeneratorIndex);
	}

	/**
	 * 
	 */
	public void setLastErrorCountInt(int lastErrorCount) {
		super.set(LAST_ERROR_COUNT, lastErrorCount);
	}
	
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
