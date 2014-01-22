package de.thatsich.bachelor.classification.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.classification.intern.command.classifier.core.IBinaryClassifier;


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
	public ListProperty<IBinaryClassifier> getBinaryClassifierListProperty();

	/**
	 * PropertyGetter of selected Binary Classifier
	 * 
	 * @return Property of selected Binary Classifier
	 */
	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty();

	/**
	 * Getter of selected Binary Classifier
	 * 
	 * @return Selected Binary Classifier
	 */
	public IBinaryClassifier getSelectedBinaryClassifier();

	/**
	 * Setter of selected Binary Classifier
	 * 
	 * @param bc
	 *            Selected Binary Classifier
	 */
	public void setSelectedBinaryClassifier( IBinaryClassifier bc );
}
