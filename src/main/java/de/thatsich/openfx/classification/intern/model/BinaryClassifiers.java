package de.thatsich.openfx.classification.intern.model;

import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;
import de.thatsich.openfx.classification.intern.control.classifier.core.IBinaryClassifier;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class BinaryClassifiers implements IBinaryClassifiers
{
	// Properties
	private final ListProperty<IBinaryClassifier> binaryClassifierList = new SimpleListProperty<>(FXCollections.<IBinaryClassifier>observableArrayList());
	private final ObjectProperty<IBinaryClassifier> selectedBinaryClassifier = new SimpleObjectProperty<>();

	// Property Getter
	@Override
	public ListProperty<IBinaryClassifier> list()
	{
		return this.binaryClassifierList;
	}

	@Override
	public ObjectProperty<IBinaryClassifier> selected()
	{
		return this.selectedBinaryClassifier;
	}
}
