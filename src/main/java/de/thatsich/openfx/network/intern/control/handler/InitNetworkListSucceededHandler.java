package de.thatsich.openfx.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.entity.Network;

import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class InitNetworkListSucceededHandler extends ACommandHandler<List<Network>>
{
	@Inject private INetworks networks;

	@Override
	public void handle(final List<Network> value)
	{
		this.networks.list().addAll(value);
		this.log.info("Added NetworkList to Database.");
	}
}
