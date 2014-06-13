package de.thatsich.openfx.preprocessed.intern.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.preprocessed.api.model.IPreProcessedVectors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public class PreProcessedVectors implements IPreProcessedVectors
{
	final private ListProperty<IFeatureVector> list = new SimpleListProperty<>(FXCollections.<IFeatureVector>observableArrayList());
	final private ObjectProperty<IFeatureVector> selected = new SimpleObjectProperty<>();

	@Override
	public ListProperty<IFeatureVector> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IFeatureVector> selected()
	{
		return this.selected;
	}
}
