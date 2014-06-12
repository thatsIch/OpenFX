package de.thatsich.openfx.network.intern.model;

import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworks;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class Networks implements INetworks
{
	final private ObjectProperty<ITrainedNetwork> selectedNetwork = new SimpleObjectProperty<>();
	final private ListProperty<ITrainedNetwork> networkList = new SimpleListProperty<>(FXCollections.<ITrainedNetwork>observableArrayList());

	@Override
	public ListProperty<ITrainedNetwork> list()
	{
		return this.networkList;
	}

	@Override
	public ObjectProperty<ITrainedNetwork> selected()
	{
		return this.selectedNetwork;
	}
}
