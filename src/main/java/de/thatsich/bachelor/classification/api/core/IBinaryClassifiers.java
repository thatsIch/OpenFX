package de.thatsich.bachelor.classification.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;

public interface IBinaryClassifiers {
	// Property Getter
	public ListProperty<IBinaryClassifier> getBinaryClassifierListProperty();
	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty();
	
	// Getter
	public IBinaryClassifier getSelectedBinaryClassifier();
	
	// Setter
	public void setSelectedBinaryClassifier(IBinaryClassifier bc);
}
