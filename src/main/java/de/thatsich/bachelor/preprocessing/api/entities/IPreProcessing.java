package de.thatsich.bachelor.preprocessing.api.entities;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import org.opencv.core.Mat;


/**
 * Interface for defining what a BinaryClassification needs to fullfill.
 * BinaryClassifications are the results of a BinaryClassifier (RF, SVM etc)
 * With that you need to be able to save a calculated one, load a previously
 * calculated one and use it to predict.
 * 
 * It offers additional access to BinaryClassificationConfiguration.
 * 
 * 
 * @author thatsIch
 */
public interface IPreProcessing
{
	/**
	 * Gets the name of the BinaryClassification
	 * 
	 * @return Name of BinaryClassification
	 */
	String getName();

	/**
	 * Uses an image (in Mat form) to predict
	 * 
	 * @param image
	 *            predicted image
	 * 
	 * @return probability of image prediction
	 */
	double predict( Mat image );

	/**
	 * Loads a BinaryClassification saved as file
	 * 
	 * @param fileName
	 *            Name of BinaryClassification File
	 */
	void load( String fileName );

	/**
	 * Saves a BinaryClassification to file
	 * 
	 * @param fileName
	 *            Name of BinaryClassification File
	 */
	void save( String fileName );

	/**
	 * Gets Property of FilePath
	 * 
	 * @return Property of FilePath
	 */
	ReadOnlyObjectProperty<Path> getFilePathProperty();

	/**
	 * Gets Property of FilePath
	 * 
	 * @return Property of FilePath
	 */
	ReadOnlyStringProperty getClassificationNameProperty();

	/**
	 * Gets Property of ExtractorName
	 * 
	 * @return Property of ExtractorName
	 */
	ReadOnlyStringProperty getExtractorNameProperty();

	/**
	 * Gets Property of FrameSize
	 * 
	 * @return Property of FrameSize
	 */
	ReadOnlyIntegerProperty getFrameSizeProperty();

	/**
	 * Gets Property of ErrorName
	 * 
	 * @return Property of ErrorName
	 */
	ReadOnlyStringProperty getErrorNameProperty();

	/**
	 * Gets Property of ID
	 * 
	 * @return Property of ID
	 */
	ReadOnlyStringProperty getIdProperty();
}
