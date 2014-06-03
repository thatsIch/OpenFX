package de.thatsich.openfx.prediction.api.model;

import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IBinaryPredictions
{
	// Property Getter
	public ListProperty<BinaryPrediction> getBinaryPredictionListProperty();

	public ObjectProperty<BinaryPrediction> getSelectedBinaryPredictionProperty();

	// Getter
	public BinaryPrediction getSelectedBinaryPrediction();

	// Setter
	public void setSelectedBinaryPrediction(BinaryPrediction prediction);
}
