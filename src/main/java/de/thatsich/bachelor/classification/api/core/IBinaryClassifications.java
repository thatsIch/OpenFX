package de.thatsich.bachelor.classification.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;

public interface IBinaryClassifications {
	// Property Getter
	public ListProperty<IBinaryClassification> getBinaryClassificationListProperty();
	public ObjectProperty<IBinaryClassification> getSelectedBinaryClassificationProperty();
	
	// Getter
	public IBinaryClassification getSelectedBinaryClassification();
	
	// Setter
	public void setSelectedBinaryClassification(IBinaryClassification bc);
}
