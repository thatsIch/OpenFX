package de.thatsich.bachelor.javafx.business.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.core.opencv.IBinaryClassifier;

public class BinaryClassifiers {
	
	// Properties
	private final ListProperty<IBinaryClassifier> binaryClassifierList = new SimpleListProperty<IBinaryClassifier>(FXCollections.<IBinaryClassifier>observableArrayList());
	private final ObjectProperty<IBinaryClassifier> selectedBinaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
		
	// Property Getter
	public ListProperty<IBinaryClassifier> getBinaryClassifierListProperty() { return this.binaryClassifierList; }
	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty() { return this.selectedBinaryClassifier; }
}
