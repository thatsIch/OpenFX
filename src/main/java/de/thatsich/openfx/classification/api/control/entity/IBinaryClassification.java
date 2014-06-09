package de.thatsich.openfx.classification.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;


/**
 * Interface for defining what a BinaryClassification needs to fullfill.
 * BinaryClassifications are the results of a BinaryClassifier (RF, SVM etc)
 * With that you need to be able to create a calculated one, retrieve a previously
 * calculated one and use it to predict.
 *
 * It offers additional access to BinaryClassificationConfiguration.
 *
 * @author thatsIch
 */
public interface IBinaryClassification extends IEntity
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
	 * @param image predicted image
	 *
	 * @return probability of image prediction
	 */
	double predict(Mat image);

	/**
	 * Saves to a full string path
	 *
	 * @param filePath full string path
	 */
	void save(String filePath);

	/**
	 * Loads from a full string path
	 *
	 * @param filePath full string path
	 */
	void load(String filePath);

	/**
	 * Gets Property of FilePath
	 *
	 * @return Property of FilePath
	 */
	ReadOnlyStringProperty classificationNameProperty();

	/**
	 * Gets Property of ExtractorName
	 *
	 * @return Property of ExtractorName
	 */
	ReadOnlyStringProperty extractorNameProperty();

	/**
	 * Gets Property of FrameSize
	 *
	 * @return Property of FrameSize
	 */
	ReadOnlyIntegerProperty tileSizeProperty();

	/**
	 * Gets Property of ErrorName
	 *
	 * @return Property of ErrorName
	 */
	ReadOnlyStringProperty errorNameProperty();

	/**
	 * Gets Property of ID
	 *
	 * @return Property of ID
	 */
	ReadOnlyStringProperty idProperty();

	/**
	 * Gets the config of the BC
	 *
	 * @return config of the BC
	 */
	@Override
	BinaryClassificationConfig getConfig();
}
