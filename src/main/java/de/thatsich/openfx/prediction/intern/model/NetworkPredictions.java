package de.thatsich.openfx.prediction.intern.model;

import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.INetworkPredictions;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class NetworkPredictions implements INetworkPredictions
{
	// Properties
	final private ObjectProperty<INetworkPrediction> selectedBinaryPrediction = new SimpleObjectProperty<>();
	final private ListProperty<INetworkPrediction> binaryPredictionList = new SimpleListProperty<>(FXCollections.<INetworkPrediction>observableArrayList());

	// Property Getter
	@Override
	public ListProperty<INetworkPrediction> list()
	{ return this.binaryPredictionList; }

	@Override
	public ObjectProperty<INetworkPrediction> selected() { return this.selectedBinaryPrediction; }
}
