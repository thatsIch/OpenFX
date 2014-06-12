package de.thatsich.openfx.classification.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;


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
public interface ITraindBinaryClassifier extends IEntity
{
	/**
	 * Gets the name of the BinaryClassification
	 *
	 * @return Name of BinaryClassification
	 */
	String getName();

	/**
	 * Uses an feature vector to predict
	 * automatically converts to mat for opencv to use
	 *
	 * @param fv feature vector
	 *
	 * @return probability of error prediction
	 */
	double predict(IFeatureVector fv);

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
