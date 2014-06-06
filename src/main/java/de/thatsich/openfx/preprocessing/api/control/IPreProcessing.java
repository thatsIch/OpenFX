package de.thatsich.openfx.preprocessing.api.control;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.encog.neural.networks.BasicNetwork;


/**
 * Result of an IPreProcessor
 *
 * @author thatsIch
 */
public interface IPreProcessing extends IEntity
{
	/**
	 * Preprocesses a featurevector to maybe optimize future classifications
	 *
	 * @param featureVector to be processed FeatureVector
	 *
	 * @return Preprocessed FeatureVector
	 */
	double[] preprocess(double[] featureVector);

	ReadOnlyStringProperty nameProperty();

	ReadOnlyIntegerProperty inputSizeProperty();

	ReadOnlyIntegerProperty outputSizeProperty();

	ReadOnlyStringProperty idProperty();

	ReadOnlyObjectProperty<BasicNetwork> networkProperty();

	@Override
	PreProcessingConfig getConfig();
}
