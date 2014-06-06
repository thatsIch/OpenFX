package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	private final ReadOnlyListProperty<Float> floats;
	private final ReadOnlyBooleanProperty isPositive;

	/**
	 * CTOR
	 *
	 * @param vector     Feature Vector
	 * @param isPositive Error Label
	 */
	public FeatureVector(List<Float> vector, boolean isPositive)
	{
		final ObservableList<Float> floats = FXCollections.observableArrayList(vector);

		this.floats = new ReadOnlyListWrapper<>(floats);
		this.isPositive = new ReadOnlyBooleanWrapper(isPositive);
	}

	@Override
	public ReadOnlyListProperty<Float> vector()
	{
		return this.floats;
	}

	@Override
	public ReadOnlyBooleanProperty isPositive()
	{
		return this.isPositive;
	}
}
