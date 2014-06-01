package de.thatsich.bachelor.network.api.guice;

import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

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

	}
}
