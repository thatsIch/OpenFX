package de.thatsich.bachelor.classification.intern.model;

import de.thatsich.bachelor.classification.api.model.IBinaryClassifiers;
import de.thatsich.bachelor.classification.intern.control.classifier.core.IBinaryClassifier;
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
	public ListProperty<IBinaryClassifier> getBinaryClassifierListProperty()
	{ return this.binaryClassifierList; }

	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty() { return this.selectedBinaryClassifier; }

	// Getter
	public IBinaryClassifier getSelectedBinaryClassifier()
	{ return this.selectedBinaryClassifier.get(); }

	// Setter
	public void setSelectedBinaryClassifier(IBinaryClassifier bc)
	{ this.selectedBinaryClassifier.set(bc); }
}
