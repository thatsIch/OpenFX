package de.thatsich.openfx.prediction.api.control.entity;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IBinaryPrediction
{
	ReadOnlyObjectProperty<Mat> modified();

	ReadOnlyObjectProperty<Mat> errorIndication();

	ReadOnlyObjectProperty<Mat> errorPrediction();

	ReadOnlyStringProperty classifierName();

	ReadOnlyStringProperty extractorName();

	ReadOnlyIntegerProperty frameSize();

	ReadOnlyStringProperty errorClassName();

	ReadOnlyStringProperty id();


	ReadOnlyIntegerProperty truePositive();

	ReadOnlyIntegerProperty trueNegative();

	ReadOnlyIntegerProperty falsePositive();

	ReadOnlyIntegerProperty falseNegative();
}
