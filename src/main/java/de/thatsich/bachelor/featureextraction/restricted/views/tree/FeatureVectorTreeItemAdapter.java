package de.thatsich.bachelor.featureextraction.restricted.views.tree;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

/**
 * Adapter for
 * - FeatureVector
 * - TreeItem
 * 
 * @author Minh
 *
 */
public class FeatureVectorTreeItemAdapter implements IFeatureSpaceTreeItemAdapter {

	// Properties
	private final ObjectProperty<FeatureVector> featureVector = new SimpleObjectProperty<>();
	
	/**
	 * CTOR
	 * 
	 * @param vector
	 */
	public FeatureVectorTreeItemAdapter(FeatureVector vector) {
		this.featureVector.set(vector);
	}
	
	
	@Override public String toString() { return this.featureVector.get().getIsPositiveProperty().get() + " - " + this.featureVector.get().getVectorProperty().get(); }
	// IFeatureSpaceTreeItem Implementation
	@Override public boolean isSet() { return false; }
	@Override public boolean isVector() { return true; }
	@Override public FeatureVectorSet getSet() { return null; }
	@Override public FeatureVector getVector() { return this.featureVector.get(); }
} 