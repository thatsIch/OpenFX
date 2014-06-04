package de.thatsich.openfx.prediction.intern.model;

import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
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
	@Override
	public ListProperty<BinaryPrediction> list()
	{ return this.binaryPredictionList; }

	@Override
	public ObjectProperty<BinaryPrediction> selected() { return this.selectedBinaryPrediction; }
}
