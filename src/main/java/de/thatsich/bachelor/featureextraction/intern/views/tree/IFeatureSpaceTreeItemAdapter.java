package de.thatsich.bachelor.featureextraction.intern.views.tree;

import de.thatsich.bachelor.featureextraction.api.control.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;

public abstract interface IFeatureSpaceTreeItemAdapter
{
	public abstract boolean isSet();

	public abstract boolean isVector();

	public abstract FeatureVectorSet getSet();

	public abstract FeatureVector getVector();
}
