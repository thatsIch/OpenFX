package de.thatsich.bachelor.featureextraction.intern.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

public class FeatureVectorSets implements IFeatureVectorSets {
	
	// Properties
	private final ListProperty<FeatureVectorSet> featureVectorSetList = new SimpleListProperty<FeatureVectorSet>(FXCollections.<FeatureVectorSet>observableArrayList());
	private final ObjectProperty<FeatureVectorSet> selectedFeatureVectorSet = new SimpleObjectProperty<FeatureVectorSet>();
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<FeatureVector>();
	private final IntegerProperty selectedIndex = new SimpleIntegerProperty();
	
	// Property Getter
	public ListProperty<FeatureVectorSet> getFeatureVectorSetListProperty() { return this.featureVectorSetList; }
	public ObjectProperty<FeatureVectorSet> getSelectedFeatureVectorSetProperty() { return this.selectedFeatureVectorSet; }
	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty() { return this.selectedFeatureVector; }
	public IntegerProperty getSelectedIndexProperty() { return this.selectedIndex; }
	
	// Getter
	public FeatureVectorSet getSelectedFeatureVectorSet() { return this.selectedFeatureVectorSet.get(); }
	public FeatureVector getSelectedFeatureVector() { return this.selectedFeatureVector.get(); }
	public int getSelectedIndex() { return this.selectedIndex.get(); }
	
	// Setter
	public void setSelectedFeatureVectorSet(FeatureVectorSet featureVectorSet) { this.selectedFeatureVectorSet.set(featureVectorSet); }
	public void setSelectedFeatureVector(FeatureVector featureVector) { this.selectedFeatureVector.set(featureVector); }
	public void setSelectedIndex(int index) { this.selectedIndex.set(index); }
}
