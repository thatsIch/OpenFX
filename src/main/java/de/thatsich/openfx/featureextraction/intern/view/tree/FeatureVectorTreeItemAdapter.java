package de.thatsich.openfx.featureextraction.intern.view.tree;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * Adapter for
 * - FeatureVector
 * - TreeItem
 *
 * @author Minh
 */
public class FeatureVectorTreeItemAdapter implements IFeatureSpaceTreeItemAdapter
{
	// Properties
	private final ObjectProperty<IFeatureVector> featureVector = new SimpleObjectProperty<>();

	/**
	 * CTOR
	 *
	 * @param vector Vector
	 */
	public FeatureVectorTreeItemAdapter(IFeatureVector vector)
	{
		this.featureVector.set(vector);
	}

	@Override
	public String toString()
	{
		final String isPositive = String.valueOf(this.featureVector.get().isPositive());
		String featureVector = this.featureVector.get().vector().toString();
		if (featureVector.length() > 100)
		{
			featureVector = featureVector.substring(0, 100) + "...";
		}

		return isPositive + " - " + featureVector;
	}

	// IFeatureSpaceTreeItem Implementation
	@Override
	public boolean isSet()
	{
		return false;
	}

	@Override
	public boolean isVector()
	{
		return true;
	}

	@Override
	public IFeature getFeature()
	{
		return null;
	}

	@Override
	public IFeatureVector getVector()
	{
		return this.featureVector.get();
	}
}