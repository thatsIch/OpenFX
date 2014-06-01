package de.thatsich.bachelor.errorgeneration.api.guice;

import de.thatsich.bachelor.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.bachelor.errorgeneration.intern.control.provider.IErrorInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

import java.util.List;


/**
 * Guice Module
 *
 * @author thatsIch
 * @see ACommandModule
 */
public class ErrorCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IErrorCommandProvider.class);
		providerList.add(IErrorInitCommandProvider.class);
	}
}
