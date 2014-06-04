package de.thatsich.openfx.featureextraction.intern.models;

import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.openfx.featureextraction.api.model.IFeatureVectorSets;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class FeatureVectorSets implements IFeatureVectorSets
{
	// Properties
	private final ListProperty<FeatureVectorSet> featureVectorSetList = new SimpleListProperty<>(FXCollections.<FeatureVectorSet>observableArrayList());
	private final ObjectProperty<FeatureVectorSet> selectedFeatureVectorSet = new SimpleObjectProperty<>();
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<>();
	private final IntegerProperty selectedIndex = new SimpleIntegerProperty();

	// Property Getter
	@Override
	public ListProperty<FeatureVectorSet> list()
	{
		return this.featureVectorSetList;
	}

	@Override
	public ObjectProperty<FeatureVectorSet> selectedSet()
	{
		return this.selectedFeatureVectorSet;
	}

	@Override
	public ObjectProperty<FeatureVector> selected()
	{
		return this.selectedFeatureVector;
	}

	@Override
	public IntegerProperty index()
	{
		return this.selectedIndex;
	}
}
