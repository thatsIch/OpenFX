package de.thatsich.bachelor.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.network.api.core.INetworks;
import de.thatsich.bachelor.network.entities.Network;
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
