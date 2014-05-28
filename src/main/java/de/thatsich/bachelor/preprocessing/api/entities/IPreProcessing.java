package de.thatsich.bachelor.preprocessing.api.entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import java.nio.file.Path;


/**
 * Result of an IPreProcessor
 *
 * @author thatsIch
 */
public interface IPreProcessing
{
	/**
	 * Preprocesses a featurevector to maybe optimize future classifications
	 *
	 * @param featureVector to be processed FeatureVector
	 *
	 * @return Preprocessed FeatureVector
	 */
	double[] preprocess(double[] featureVector);

	/**
	 * Gets the name of the PreProcessing
	 *
	 * @return Name of the PreProcessing
	 */
	String getName();

	/**
	 * Loads a PreProcessing from a FileName
	 *
	 * @param fileName Name of the PreProcessing File
	 */
	void load(String fileName);

	/**
	 * Saves a PreProcessing to a FileName
	 *
	 * @param fileName Name of the PreProcessing File
	 */
	void save(String fileName);

	ReadOnlyObjectProperty<Path> getFilePathProperty();

	ReadOnlyStringProperty getPreProcessingNameProperty();

	ReadOnlyIntegerProperty getInputSizeProperty();

	ReadOnlyIntegerProperty getOutputSizeProperty();

	ReadOnlyStringProperty getIdProperty();
}
