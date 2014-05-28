package de.thatsich.bachelor.prediction.intern.service;

import de.thatsich.core.AConfigurationService;


/**
 * @author Tran Minh Do
 */
public class BinaryPredictionConfigService extends AConfigurationService
{

	/**
	 *
	 */
	private static final String LAST_BINARY_PREDICTION_INDEX = "last_binary_prediction_index";

	/**
	 *
	 */
	private static final int DEFAULT_LAST_BINARY_PREDICTION_INDEX = 0;


	// ================================================== 
	// Getter Implementation 
	// ==================================================

	/**
	 *
	 */
	public int getLastBinaryPredictionIndexInt()
	{
		return super.get(LAST_BINARY_PREDICTION_INDEX, DEFAULT_LAST_BINARY_PREDICTION_INDEX);
	}


	// ================================================== 
	// Setter Implementation 
	// ==================================================

	/**
	 *
	 */
	public void setLastBinaryPredictionIndexInt(int lastBinaryPredictionIndex)
	{
		super.set(LAST_BINARY_PREDICTION_INDEX, lastBinaryPredictionIndex);
	}

}
