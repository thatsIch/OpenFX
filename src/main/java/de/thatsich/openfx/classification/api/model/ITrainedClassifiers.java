package de.thatsich.openfx.classification.api.model;

import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;


/**
 * Interface for BinaryClassifcations Model.
 *
 * Enables access to the Binary Classification List Property and
 * has Getter, Setter and Property for the currently selected one
 *
 * @author thatsIch
 */
public interface ITrainedClassifiers
{
	/**
	 * PropertyGetter of Binary Classification List
	 *
	 * @return ListProperty of Binary Classifications
	 */
	public ListProperty<ITrainedBinaryClassifier> list();

	/**
	 * PropertyGetter of selected Binary Classification
	 *
	 * @return Property of selected Binary Classification
	 */
	public ObjectProperty<ITrainedBinaryClassifier> selected();
}
