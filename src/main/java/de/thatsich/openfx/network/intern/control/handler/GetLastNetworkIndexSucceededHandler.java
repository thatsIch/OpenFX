package de.thatsich.openfx.network.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworks;

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
		if (value >= 0 && this.networks.list().size() > 0)
		{
			final ITrainedNetwork selectedNetwork = this.networks.list().get(value);
			this.networks.selected().set(selectedNetwork);
			this.log.info("Set Network in Model.");
		}
	}
}
