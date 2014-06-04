package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.IFeatureVector;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

import java.util.List;

/**
 * Represents a FeatureVector with serveral information
 * how it was created.
 *
 * @author Minh
 */
public class FeatureVector implements IFeatureVector
{
	// Properties
	private final ReadOnlyListWrapper<Float> vector = new ReadOnlyListWrapper<>(FXCollections.<Float>observableArrayList());
	private final ReadOnlyBooleanWrapper isPositive = new ReadOnlyBooleanWrapper();

	/**
	 * CTOR
	 *
	 * @param vector     Feature Vector
	 * @param isPositive Error Label
	 */
	public FeatureVector(List<Float> vector, boolean isPositive)
	{
		this.vector.setAll(vector);
		this.isPositive.set(isPositive);
	}

	public ReadOnlyBooleanProperty isPositive()
	{
		return this.isPositive.getReadOnlyProperty();
	}

	// Property Getter
	public ReadOnlyListProperty<Float> vector()
	{
		return this.vector.getReadOnlyProperty();
	}
}
