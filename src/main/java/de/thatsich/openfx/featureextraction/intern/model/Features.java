package de.thatsich.openfx.featureextraction.intern.model;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
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
	final private ListProperty<IFeature> list = new SimpleListProperty<>(FXCollections.<IFeature>observableArrayList());
	final private ObjectProperty<IFeature> selected = new SimpleObjectProperty<>();

	// Property Getter
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
