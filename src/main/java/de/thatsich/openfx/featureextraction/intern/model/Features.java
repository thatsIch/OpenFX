package de.thatsich.openfx.featureextraction.intern.model;

import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
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
	final private ListProperty<Feature> list = new SimpleListProperty<>(FXCollections.<Feature>observableArrayList());
	final private ObjectProperty<Feature> selected = new SimpleObjectProperty<>();

	// Property Getter
	@Override
	public ListProperty<Feature> list()
	{
		return this.list;
	}

	@Override
	public ObjectProperty<Feature> selected()
	{
		return this.selected;
	}
}
