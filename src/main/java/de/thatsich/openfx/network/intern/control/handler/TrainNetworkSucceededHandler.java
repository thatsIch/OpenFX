package de.thatsich.openfx.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.network.api.control.Network;
import de.thatsich.openfx.network.api.model.INetworks;

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
		this.networks.list().add(value);
		this.log.info("Added Network to Database.");

		this.networks.selected().set(value);
		this.log.info("Set current to selected Network.");
	}
}
