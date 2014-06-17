package de.thatsich.openfx.prediction.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPredictionConfig;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface INetworkPrediction extends IEntity
{
	ReadOnlyObjectProperty<Mat> modified();

	ReadOnlyObjectProperty<IError[][]> errors();

	ReadOnlyObjectProperty<String[][]> errorClasses();

	ReadOnlyObjectProperty<Double[][]> errorPredictions();

	ReadOnlyStringProperty dateTime();

	ReadOnlyStringProperty predictedClassName();

	ReadOnlyStringProperty id();

	@Override
	NetworkPredictionConfig getConfig();
}
