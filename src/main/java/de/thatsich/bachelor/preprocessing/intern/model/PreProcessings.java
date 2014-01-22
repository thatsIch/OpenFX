package de.thatsich.bachelor.preprocessing.intern.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;

public class PreProcessings implements IBinaryClassifications {
	// Properties
	private final ListProperty<IBinaryClassification> binaryClassificationList = new SimpleListProperty<IBinaryClassification>(FXCollections.<IBinaryClassification>observableArrayList());
	private final ObjectProperty<IBinaryClassification> selectedBinaryClassification = new SimpleObjectProperty<IBinaryClassification>();
		
	// Property Getter
	public ListProperty<IBinaryClassification> getBinaryClassificationListProperty() { return this.binaryClassificationList; }
	public ObjectProperty<IBinaryClassification> getSelectedBinaryClassificationProperty() { return this.selectedBinaryClassification; }
	
	// Getter
	public IBinaryClassification getSelectedBinaryClassification() { return this.selectedBinaryClassification.get(); }
	
	// Setter
	public void setSelectedBinaryClassification(IBinaryClassification bc) { this.selectedBinaryClassification.set(bc); }
}
