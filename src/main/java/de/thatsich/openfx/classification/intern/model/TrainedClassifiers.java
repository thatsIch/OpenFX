package de.thatsich.openfx.classification.intern.model;

import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class TrainedClassifiers implements ITrainedClassifiers
{
	// Properties
	private final ListProperty<ITrainedBinaryClassifier> binaryClassificationList = new SimpleListProperty<>(FXCollections.<ITrainedBinaryClassifier>observableArrayList());
	private final ObjectProperty<ITrainedBinaryClassifier> selectedBinaryClassification = new SimpleObjectProperty<>();

	// Property Getter
	public ListProperty<ITrainedBinaryClassifier> list()
	{
		return this.binaryClassificationList;
	}

	public ObjectProperty<ITrainedBinaryClassifier> selected()
	{
		return this.selectedBinaryClassification;
	}

}
