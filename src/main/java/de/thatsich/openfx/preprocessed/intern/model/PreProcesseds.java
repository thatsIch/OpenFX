package de.thatsich.openfx.preprocessed.intern.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public class PreProcesseds implements IPreProcesseds
{
	// Properties
	private final ListProperty<IFeature> list = new SimpleListProperty<>(FXCollections.<IFeature>observableArrayList());
	private final ObjectProperty<IFeature> selected = new SimpleObjectProperty<>();

	@Override
	public ListProperty<IFeature> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<IFeature> selected()
	{
		return this.selected;
	}
}
