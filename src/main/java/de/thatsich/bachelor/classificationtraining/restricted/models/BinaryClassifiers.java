package de.thatsich.bachelor.classificationtraining.restricted.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;

public class BinaryClassifiers {
	
	// Properties
	private final ListProperty<IBinaryClassifier> binaryClassifierList = new SimpleListProperty<IBinaryClassifier>(FXCollections.<IBinaryClassifier>observableArrayList());
	private final ObjectProperty<IBinaryClassifier> selectedBinaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
		
	// Property Getter
	public ListProperty<IBinaryClassifier> getBinaryClassifierListProperty() { return this.binaryClassifierList; }
	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty() { return this.selectedBinaryClassifier; }
}
