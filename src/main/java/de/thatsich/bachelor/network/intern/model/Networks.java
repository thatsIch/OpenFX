package de.thatsich.bachelor.network.intern.model;

import de.thatsich.bachelor.network.api.model.INetworks;
import de.thatsich.bachelor.network.api.control.Network;
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
	public ListProperty<Network> getNetworkListProperty()
	{
		return this.networkList;
	}

	@Override
	public ObjectProperty<Network> getSelectedNetworkProperty()
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
