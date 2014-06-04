package de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core;

import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;


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
	IPreProcessing train(double[][] trainData, double[][] idealData, PreProcessorConfiguration config);

	/**
	 * Gets the Name of the PreProcessor
	 *
	 * @return Name of the PreProcessor
	 */
	String getName();
}