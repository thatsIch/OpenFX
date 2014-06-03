package de.thatsich.openfx.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.api.control.Network;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class GetLastNetworkIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject
	private INetworks networks;

	@Override
	public void handle(final Integer value)
	{
		final Network selectedNetwork = this.networks.getNetworkListProperty().get(value);
		this.networks.setSelectedNetwork(selectedNetwork);
		this.log.info("Set Network in Model.");
	}
}
