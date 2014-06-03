package de.thatsich.openfx.prediction.intern.model;

import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class BinaryPredictions implements IBinaryPredictions
{

	// Properties
	final private ObjectProperty<BinaryPrediction> selectedBinaryPrediction = new SimpleObjectProperty<>();
	final private ListProperty<BinaryPrediction> binaryPredictionList = new SimpleListProperty<>(FXCollections.<BinaryPrediction>observableArrayList());

	// Property Getter
	public ListProperty<BinaryPrediction> getBinaryPredictionListProperty()
	{ return this.binaryPredictionList; }

	public ObjectProperty<BinaryPrediction> getSelectedBinaryPredictionProperty() { return this.selectedBinaryPrediction; }

	// Getter
	public BinaryPrediction getSelectedBinaryPrediction()
	{ return this.selectedBinaryPrediction.get(); }

	// Setter
	public void setSelectedBinaryPrediction(BinaryPrediction prediction)
	{ this.selectedBinaryPrediction.set(prediction); }
}
