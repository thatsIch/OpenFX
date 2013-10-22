package de.thatsich.bachelor.classificationtraining.restricted.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.classificationtraining.api.entities.TrainedBinaryClassifier;

public class TrainedBinaryClassifiers {
	// Properties
	private final ListProperty<TrainedBinaryClassifier> trainedBinaryClassifierList = new SimpleListProperty<TrainedBinaryClassifier>(FXCollections.<TrainedBinaryClassifier>observableArrayList());
	private final ObjectProperty<TrainedBinaryClassifier> selectedTrainedBinaryClassifier = new SimpleObjectProperty<TrainedBinaryClassifier>();
		
	// Property Getter
	public ListProperty<TrainedBinaryClassifier> getTrainedBinaryClassifierListProperty() { return this.trainedBinaryClassifierList; }
	public ObjectProperty<TrainedBinaryClassifier> getSelectedTrainedBinaryClassifierProperty() { return this.selectedTrainedBinaryClassifier; }
}
