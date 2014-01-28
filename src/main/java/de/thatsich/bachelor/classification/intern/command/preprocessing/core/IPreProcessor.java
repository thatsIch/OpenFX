package de.thatsich.bachelor.classification.intern.command.preprocessing.core;

import org.opencv.core.MatOfFloat;


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
	 * @param testData
	 *            Ideal Result Data
	 * @return Trained PreProcessing
	 */
	IPreProcessing train( MatOfFloat trainData, MatOfFloat testData );

	/**
	 * Gets the Name of the PreProcessor
	 * 
	 * @return Name of the PreProcessor
	 */
	String getName();
}
