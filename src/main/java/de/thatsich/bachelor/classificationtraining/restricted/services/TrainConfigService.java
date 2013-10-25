package de.thatsich.bachelor.classificationtraining.restricted.services;

import de.thatsich.core.AConfiguration;


/**
 * 
 * @author Tran Minh Do
 *
 */
public class TrainConfigService extends AConfiguration {

	/**
	 * 
	 */
	private static final String LAST_BINARY_CLASSIFIER_INDEX = "last_binary_classifier_index";
	private static final String LAST_BINARY_CLASSIFICATION_INDEX = "last_binary_classification_index";
	
	/**
	 * 
	 */
	private static final int DEFAULT_LAST_BINARY_CLASSIFIER_INDEX = 0;
	private static final int DEFAULT_LAST_BINARY_CLASSIFICATION_INDEX = 0;
	
	
	// ================================================== 
	// Getter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public int getLastBinaryClassifierIndexInt() {
		return super.get(LAST_BINARY_CLASSIFIER_INDEX, DEFAULT_LAST_BINARY_CLASSIFIER_INDEX);
	}
	
	public int getLastBinaryClassificationIndexInt() {
		return super.get(LAST_BINARY_CLASSIFICATION_INDEX, DEFAULT_LAST_BINARY_CLASSIFICATION_INDEX);
	}
	
	// ================================================== 
	// Setter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public void setLastBinaryClassifierIndexInt(int lastBinaryClassifierIndex) {
		super.set(LAST_BINARY_CLASSIFIER_INDEX, lastBinaryClassifierIndex);
	}
	
	public void setLastBinaryClassificationIndexInt(int lastTrainedBinaryClassifierIndex) {
		super.set(LAST_BINARY_CLASSIFICATION_INDEX, lastTrainedBinaryClassifierIndex);
	}
}
