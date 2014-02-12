package de.thatsich.bachelor.featureextraction.intern.views.tree;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

/**
 * Adapter for 
 * - FeatureVectorSet
 * - TreeItem
 * 
 * @author Minh
 *
 */
public class FeatureVectorSetTreeItemAdapter implements IFeatureSpaceTreeItemAdapter {

	// Properties
	private final ObjectProperty<FeatureVectorSet> featureVectorSet = new SimpleObjectProperty<>();
	
	/**
	 * CTOR
	 * 
	 * @param set
	 */
	public FeatureVectorSetTreeItemAdapter(FeatureVectorSet set) {
		this.featureVectorSet.set(set);
	}
	
	@Override public String toString() { return this.featureVectorSet.get().getClassNameProperty().get() + " - " + this.featureVectorSet.get().getExtractorNameProperty().get() + " - " + this.featureVectorSet.get().getFrameSizeProperty().get() + "px - " + this.featureVectorSet.get().getIdProperty().get(); } 
	
	// IFeatureSpaceTreeItemAdapter Implementation
	@Override public boolean isSet() { return true; }
	@Override public boolean isVector() { return false; }
	@Override public FeatureVectorSet getSet() { return this.featureVectorSet.get(); }
	@Override public FeatureVector getVector() { return null; }
}
