package de.thatsich.openfx.prediction.intern.model;

import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class BinaryPredictions implements IBinaryPredictions
{
	// Properties
	final private ObjectProperty<IBinaryPrediction> selectedBinaryPrediction = new SimpleObjectProperty<>();
	final private ListProperty<IBinaryPrediction> binaryPredictionList = new SimpleListProperty<>(FXCollections.<IBinaryPrediction>observableArrayList());

	// Property Getter
	@Override
	public ListProperty<IBinaryPrediction> list()
	{ return this.binaryPredictionList; }

	@Override
	public ObjectProperty<IBinaryPrediction> selected() { return this.selectedBinaryPrediction; }
}
