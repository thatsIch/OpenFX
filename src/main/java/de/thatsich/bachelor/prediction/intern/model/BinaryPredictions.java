package de.thatsich.bachelor.prediction.intern.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;

public class BinaryPredictions {
	
	// Properties
	final private ObjectProperty<BinaryPrediction> selectedBinaryPrediction = new SimpleObjectProperty<>();
	final private ListProperty<BinaryPrediction> binaryPredictionList = new SimpleListProperty<>(FXCollections.<BinaryPrediction>observableArrayList());

	// Property Getter
	public ListProperty<BinaryPrediction> getBinaryPredictionListProperty() { return this.binaryPredictionList; }
	public ObjectProperty<BinaryPrediction> getSelectedBinaryPredictionProperty() { return this.selectedBinaryPrediction; }	
}
