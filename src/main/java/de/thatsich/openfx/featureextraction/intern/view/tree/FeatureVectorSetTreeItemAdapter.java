package de.thatsich.openfx.featureextraction.intern.view.tree;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Adapter for
 * - FeatureVectorSet
 * - TreeItem
 *
 * @author Minh
 */
public class FeatureVectorSetTreeItemAdapter implements IFeatureSpaceTreeItemAdapter
{
	// Properties
	private final ObjectProperty<IFeature> feature = new SimpleObjectProperty<>();

	/**
	 * CTOR
	 *
	 * @param feature vector set
	 */
	public FeatureVectorSetTreeItemAdapter(IFeature feature)
	{
		this.feature.set(feature);
	}

	@Override
	public String toString()
	{
		return this.feature.get().className() + " - " + this.feature.get().extractorName() + " - " + this.feature.get().frameSize() + "px";
	}

	// IFeatureSpaceTreeItemAdapter Implementation
	@Override
	public boolean isSet() { return true; }

	@Override
	public boolean isVector() { return false; }

	@Override
	public IFeature getFeature() { return this.feature.get(); }

	@Override
	public IFeatureVector getVector() { return null; }
}
