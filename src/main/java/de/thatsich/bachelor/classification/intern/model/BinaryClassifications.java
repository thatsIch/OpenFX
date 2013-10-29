package de.thatsich.bachelor.classification.intern.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassification;

public class BinaryClassifications {
	// Properties
	private final ListProperty<IBinaryClassification> trainedBinaryClassifierList = new SimpleListProperty<IBinaryClassification>(FXCollections.<IBinaryClassification>observableArrayList());
	private final ObjectProperty<IBinaryClassification> selectedTrainedBinaryClassifier = new SimpleObjectProperty<IBinaryClassification>();
		
	// Property Getter
	public ListProperty<IBinaryClassification> getBinaryClassificationListProperty() { return this.trainedBinaryClassifierList; }
	public ObjectProperty<IBinaryClassification> getSelectedBinaryClassificationProperty() { return this.selectedTrainedBinaryClassifier; }
}
