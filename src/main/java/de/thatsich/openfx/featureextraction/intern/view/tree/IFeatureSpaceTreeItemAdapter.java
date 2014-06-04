package de.thatsich.openfx.featureextraction.intern.view.tree;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;

public abstract interface IFeatureSpaceTreeItemAdapter
{
	public abstract boolean isSet();

	public abstract boolean isVector();

	public abstract IFeature getFeature();

	public abstract IFeatureVector getVector();
}
