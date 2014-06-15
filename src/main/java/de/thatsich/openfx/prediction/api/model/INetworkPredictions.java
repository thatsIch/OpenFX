package de.thatsich.openfx.prediction.api.model;

import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface INetworkPredictions
{
	// Property Getter
	public ListProperty<INetworkPrediction> list();

	public ObjectProperty<INetworkPrediction> selected();
}
