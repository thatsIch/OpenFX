package de.thatsich.openfx.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.network.api.control.Network;
import de.thatsich.openfx.network.api.model.INetworks;

import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class DeleteNetworkSucceededHandler extends ACommandHandler<Network>
{
	@Inject private INetworks networks;

	@Override
	public void handle(final Network value)
	{
		final List<Network> networkList = this.networks.list();
		networkList.remove(value);
		this.log.info("Removed Network from List.");

		if (networkList.size() > 0)
		{
			final Network first = networkList.get(0);
			this.networks.selected().set(first);
			this.log.info("Reset to first Network.");
		}
	}
}
