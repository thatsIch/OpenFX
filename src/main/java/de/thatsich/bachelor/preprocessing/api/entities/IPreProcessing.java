package de.thatsich.bachelor.preprocessing.api.entities;



/**
 * Result of an IPreProcessor
 * 
 * 
 * @author thatsIch
 */
public interface IPreProcessing
{
	/**
	 * Preprocesses a featurevector to maybe optimize future classifications
	 * 
	 * @param featureVector
	 *            to be processed FeatureVector
	 * 
	 * @return Preprocessed FeatureVector
	 */
	double[] preprocess( double[] featureVector );

	/**
	 * Gets the name of the PreProcessing
	 * 
	 * @return Name of the PreProcessing
	 */
	String getName();

	/**
	 * Loads a PreProcessing from a FileName
	 * 
	 * @param fileName
	 *            Name of the PreProcessing File
	 */
	void load( String fileName );

	/**
	 * Saves a PreProcessing to a FileName
	 * 
	 * @param fileName
	 *            Name of the PreProcessing File
	 */
	void save( String fileName );
}