package de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core;

import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;



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
	 * @param trainData
	 *            Train Data
	 * @param idealData
	 *            Ideal Result Data
	 * @return Trained PreProcessing
	 */
	IPreProcessing train( double[][] trainData, double[][] idealData );

	/**
	 * Gets the Name of the PreProcessor
	 * 
	 * @return Name of the PreProcessor
	 */
	String getName();
}
