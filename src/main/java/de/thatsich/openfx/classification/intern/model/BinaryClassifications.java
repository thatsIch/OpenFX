package de.thatsich.openfx.classification.intern.model;

import de.thatsich.openfx.classification.api.control.entity.ITraindBinaryClassifier;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class BinaryClassifications implements IBinaryClassifications
{
	// Properties
	private final ListProperty<ITraindBinaryClassifier> binaryClassificationList = new SimpleListProperty<>(FXCollections.<ITraindBinaryClassifier>observableArrayList());
	private final ObjectProperty<ITraindBinaryClassifier> selectedBinaryClassification = new SimpleObjectProperty<>();

	// Property Getter
	public ListProperty<ITraindBinaryClassifier> list()
	{
		return this.binaryClassificationList;
	}

	public ObjectProperty<ITraindBinaryClassifier> selected()
	{
		return this.selectedBinaryClassification;
	}

}
