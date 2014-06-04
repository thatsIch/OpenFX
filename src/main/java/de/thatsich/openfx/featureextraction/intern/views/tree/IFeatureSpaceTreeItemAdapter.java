package de.thatsich.openfx.featureextraction.intern.views.tree;

import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;

public abstract interface IFeatureSpaceTreeItemAdapter
{
	public abstract boolean isSet();

	public abstract boolean isVector();

	public abstract FeatureVectorSet getSet();

	public abstract FeatureVector getVector();
}
