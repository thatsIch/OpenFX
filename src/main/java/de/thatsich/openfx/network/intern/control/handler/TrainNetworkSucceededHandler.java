package de.thatsich.openfx.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.prediction.TrainedNetwork;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class TrainNetworkSucceededHandler extends ACommandHandler<TrainedNetwork>
{
	@Inject private INetworks networks;

	@Override
	public void handle(final TrainedNetwork value)
	{
		this.networks.list().add(value);
		this.log.info("Added Network to Database.");

		this.networks.selected().set(value);
		this.log.info("Set current to selected Network.");
	}
}
