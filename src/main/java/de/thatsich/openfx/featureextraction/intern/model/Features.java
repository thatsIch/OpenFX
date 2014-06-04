package de.thatsich.openfx.featureextraction.intern.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public class Features implements IFeatures
{
	// Properties
	final private ListProperty<IFeature> list = new SimpleListProperty<>(FXCollections.<IFeature>observableArrayList());
	final private ObjectProperty<IFeature> selectedFeature = new SimpleObjectProperty<>();
	final private ObjectProperty<IFeatureVector> selectedFeatureVector = new SimpleObjectProperty<>();
	final private IntegerProperty index = new SimpleIntegerProperty();


	// Property Getter
	@Override
	public ListProperty<IFeature> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IFeature> selectedFeature()
	{
		return this.selectedFeature;
	}

	@Override
	public ObjectProperty<IFeatureVector> selectedFeatureVector()
	{
		return this.selectedFeatureVector;
	}

	@Override
	public IntegerProperty index()
	{
		return this.index;
	}
}
