package de.thatsich.bachelor.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.network.api.model.INetworks;
import de.thatsich.bachelor.network.api.control.Network;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class TrainNetworkSucceededHandler extends ACommandHandler<Network>
{
	@Inject private INetworks networks;

	@Override
	public void handle(final Network value)
	{
		this.networks.getNetworkListProperty().add(value);
		this.log.info("Added Network to Database.");

		this.networks.setSelectedNetwork(value);
		this.log.info("Set current to selected Network.");
	}
}
