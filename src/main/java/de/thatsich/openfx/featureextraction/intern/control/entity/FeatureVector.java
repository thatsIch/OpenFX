package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.IFeatureVector;

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
	private final List<Float> floats;
	private final boolean isPositive;

	/**
	 * CTOR
	 *
	 * @param vector     Feature Vector
	 * @param isPositive Error Label
	 */
	public FeatureVector(List<Float> vector, boolean isPositive)
	{
		this.floats = vector;
		this.isPositive = isPositive;
	}

	@Override
	public List<Float> vector()
	{
		return this.floats;
	}

	@Override
	public boolean isPositive()
	{
		return this.isPositive;
	}
}
