package de.thatsich.openfx.featureextraction.intern.views.tree;

import de.thatsich.openfx.featureextraction.api.control.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
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
	private final ObjectProperty<FeatureVectorSet> featureVectorSet = new SimpleObjectProperty<>();

	/**
	 * CTOR
	 *
	 * @param set vector set
	 */
	public FeatureVectorSetTreeItemAdapter(FeatureVectorSet set)
	{
		this.featureVectorSet.set(set);
	}

	@Override
	public String toString() { return this.featureVectorSet.get().className().get() + " - " + this.featureVectorSet.get().extractorName().get() + " - " + this.featureVectorSet.get().frameSize().get() + "px - " + this.featureVectorSet.get().id().get(); }

	// IFeatureSpaceTreeItemAdapter Implementation
	@Override
	public boolean isSet() { return true; }

	@Override
	public boolean isVector() { return false; }

	@Override
	public FeatureVectorSet getSet() { return this.featureVectorSet.get(); }

	@Override
	public FeatureVector getVector() { return null; }
}
