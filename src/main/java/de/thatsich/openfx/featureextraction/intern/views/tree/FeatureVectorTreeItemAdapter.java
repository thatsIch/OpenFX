package de.thatsich.openfx.featureextraction.intern.views.tree;

import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
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
	private final ObjectProperty<FeatureVector> featureVector = new SimpleObjectProperty<>();

	/**
	 * CTOR
	 *
	 * @param vector Vector
	 */
	public FeatureVectorTreeItemAdapter(FeatureVector vector)
	{
		this.featureVector.set(vector);
	}

	@Override
	public String toString()
	{
		final String isPositive = this.featureVector.get().isPositive().getValue().toString();
		String featureVector = this.featureVector.get().vector().get().toString();
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
	public FeatureVectorSet getSet()
	{
		return null;
	}

	@Override
	public FeatureVector getVector()
	{
		return this.featureVector.get();
	}
}