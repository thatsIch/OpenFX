package de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core;

import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;


/**
 * Interface for a PreProcessor to train a PreProcessor
 *
 * @author thatsIch
 */
public interface IPreProcessor
{
	/**
	 * Trains the PreProcessor to get a PreProcessing
	 *
	 * @param trainData Train Data
	 * @param idealData Ideal Result Data
	 *
	 * @return Trained PreProcessing
	 */
	ITrainedPreProcessor train(double[][] trainData, double[][] idealData, PreProcessingConfig config);

	/**
	 * Gets the Name of the PreProcessor
	 *
	 * @return Name of the PreProcessor
	 */
	String getName();
}
