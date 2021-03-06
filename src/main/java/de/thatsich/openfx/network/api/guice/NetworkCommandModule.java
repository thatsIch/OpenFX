package de.thatsich.openfx.network.api.guice;

import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import de.thatsich.openfx.network.intern.control.provider.INetworkInitCommandProvider;

import java.util.List;

/**
 * @author thatsIch
 * @since 31.05.2014.
 */
public class NetworkCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(final List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(INetworkCommandProvider.class);
		providerList.add(INetworkInitCommandProvider.class);
	}
}
