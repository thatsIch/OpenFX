package de.thatsich.openfx.preprocessing.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.TrainedPreProcessorConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.encog.neural.networks.BasicNetwork;


/**
 * Result of an IPreProcessor
 *
 * @author thatsIch
 */
public interface ITrainedPreProcessor extends IEntity
{
	/**
	 * Preprocesses a featurevector to maybe optimize future classifications
	 *
	 * @param feature to be processed feature
	 *
	 * @return Preprocessed feature
	 */
	IFeature preprocess(IFeature feature);

	ReadOnlyStringProperty nameProperty();

	ReadOnlyIntegerProperty inputSizeProperty();

	ReadOnlyIntegerProperty outputSizeProperty();

	ReadOnlyStringProperty idProperty();

	ReadOnlyObjectProperty<BasicNetwork> networkProperty();

	@Override
	TrainedPreProcessorConfig getConfig();
}
