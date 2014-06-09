package de.thatsich.openfx.network.intern.model;

import de.thatsich.openfx.network.api.control.entity.INetwork;
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
	final private ObjectProperty<INetwork> selectedNetwork = new SimpleObjectProperty<>();
	final private ListProperty<INetwork> networkList = new SimpleListProperty<>(FXCollections.<INetwork>observableArrayList());

	@Override
	public ListProperty<INetwork> list()
	{
		return this.networkList;
	}

	@Override
	public ObjectProperty<INetwork> selected()
	{
		return this.selectedNetwork;
	}
}
