package de.thatsich.bachelor.featureextraction.api.entities;

import java.util.List;

import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
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
	private final ReadOnlyFloatWrapper label = new ReadOnlyFloatWrapper();
	
	/**
	 * CTOR
	 * 
	 * @param vector Feature Vector
	 * @param label Error Label
	 */
	public FeatureVector(List<Float> vector, float label) {
		this.vector.setAll(vector);
		this.label.set(label);
	}

	// Property Getter
	public ReadOnlyListProperty<Float> getVectorProperty() { return this.vector.getReadOnlyProperty(); }
	public ReadOnlyFloatProperty getLabelProperty() { return this.label.getReadOnlyProperty(); }
}
