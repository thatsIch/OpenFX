package de.thatsich.bachelor.featureextraction.api.entities;

import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

/**
 * Represents a FeatureVector with serveral information 
 * how it was created.
 * 
 * @author Minh
 *
 */
public class FeatureVector {
	
	// Properties
	private final ReadOnlyListWrapper<Float> vector = new ReadOnlyListWrapper<Float>(FXCollections.<Float>observableArrayList());
	private final ReadOnlyBooleanWrapper isPositive = new ReadOnlyBooleanWrapper();
	
	/**
	 * CTOR
	 * 
	 * @param vector Feature Vector
	 * @param isPositive Error Label
	 */
	public FeatureVector(List<Float> vector, boolean isPositive) {
		this.vector.setAll(vector);
		this.isPositive.set(isPositive);
	}

	// Property Getter
	public ReadOnlyListProperty<Float> getVectorProperty() { return this.vector.getReadOnlyProperty(); }
	public ReadOnlyBooleanProperty getIsPositiveProperty() { return this.isPositive.getReadOnlyProperty(); }
}
