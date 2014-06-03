package de.thatsich.openfx.classification.intern.model;

import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class BinaryClassifications implements IBinaryClassifications
{
	// Properties
	private final ListProperty<IBinaryClassification> binaryClassificationList = new SimpleListProperty<>(FXCollections.<IBinaryClassification>observableArrayList());
	private final ObjectProperty<IBinaryClassification> selectedBinaryClassification = new SimpleObjectProperty<>();

	// Property Getter
	public ListProperty<IBinaryClassification> binaryClassifications()
	{
		return this.binaryClassificationList;
	}

	public ObjectProperty<IBinaryClassification> selectedBinaryClassification()
	{
		return this.selectedBinaryClassification;
	}

}
