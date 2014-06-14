package de.thatsich.openfx.prediction.api.model;

import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IBinaryPredictions
{
	// Property Getter
	public ListProperty<IBinaryPrediction> list();

	public ObjectProperty<IBinaryPrediction> selected();
}
