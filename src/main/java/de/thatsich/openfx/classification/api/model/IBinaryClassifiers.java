package de.thatsich.openfx.classification.api.model;

import de.thatsich.openfx.classification.intern.control.classifier.core.IBinaryClassifier;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;


/**
 * Interface for BinaryClassifier Model.
 *
 * Enables access to the Binary Classifier List Property and
 * has Getter, Setter and Property for the currently selected one
 *
 * @author thatsIch
 */
public interface IBinaryClassifiers
{
	/**
	 * PropertyGetter of Binary Classifier List
	 *
	 * @return ListProperty of Binary Classifiers
	 */
	public ListProperty<IBinaryClassifier> list();

	/**
	 * PropertyGetter of selected Binary Classifier
	 *
	 * @return Property of selected Binary Classifier
	 */
	public ObjectProperty<IBinaryClassifier> selected();
}
