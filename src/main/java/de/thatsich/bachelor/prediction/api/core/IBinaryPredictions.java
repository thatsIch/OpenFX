package de.thatsich.bachelor.prediction.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;

public interface IBinaryPredictions {
	// Property Getter
	public ListProperty<BinaryPrediction> getBinaryPredictionListProperty();
	public ObjectProperty<BinaryPrediction> getSelectedBinaryPredictionProperty();
	
	// Getter
	public BinaryPrediction getSelectedBinaryPrediction();
	
	// Setter
	public void setSelectedBinaryPrediction(BinaryPrediction prediction);
}
