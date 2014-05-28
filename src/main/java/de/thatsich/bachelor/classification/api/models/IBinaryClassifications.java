package de.thatsich.bachelor.classification.api.models;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
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
public interface IBinaryClassifications
{
	/**
	 * PropertyGetter of Binary Classification List
	 *
	 * @return ListProperty of Binary Classifications
	 */
	public ListProperty<IBinaryClassification> getBinaryClassificationListProperty();

	/**
	 * PropertyGetter of selected Binary Classification
	 *
	 * @return Property of selected Binary Classification
	 */
	public ObjectProperty<IBinaryClassification> getSelectedBinaryClassificationProperty();

	/**
	 * Getter of selected Binary Classification
	 *
	 * @return Selected Binary Classification
	 */
	public IBinaryClassification getSelectedBinaryClassification();

	/**
	 * Setter of selected Binary Classification
	 *
	 * @param bc Selected Binary Classification
	 */
	public void setSelectedBinaryClassification(IBinaryClassification bc);
}
