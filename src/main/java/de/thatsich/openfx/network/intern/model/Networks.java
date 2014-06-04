package de.thatsich.openfx.network.intern.model;

import de.thatsich.openfx.network.api.control.Network;
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
	final private ObjectProperty<Network> selectedNetwork = new SimpleObjectProperty<>();
	final private ListProperty<Network> networkList = new SimpleListProperty<>(FXCollections.<Network>observableArrayList());

	@Override
	public ListProperty<Network> list()
	{
		return this.networkList;
	}

	@Override
	public ObjectProperty<Network> selected()
	{
		return this.selectedNetwork;
	}

	@Override
	public Network getSelectedNetwork()
	{
		return this.selectedNetwork.get();
	}

	@Override
	public void setSelectedNetwork(final Network network)
	{
		this.selectedNetwork.set(network);
	}
}
