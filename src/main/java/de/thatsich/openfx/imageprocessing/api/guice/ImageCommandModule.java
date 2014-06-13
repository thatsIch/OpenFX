package de.thatsich.openfx.imageprocessing.api.guice;

import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.imageprocessing.intern.control.command.provider.IImageCommandProvider;
import de.thatsich.openfx.imageprocessing.intern.control.command.provider.IImageInitCommandProvider;

import java.util.List;


/**
 * Guice Module
 *
 * @author thatsIch
 * @see ACommandModule
 */
public class ImageCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IImageCommandProvider.class);
		providerList.add(IImageInitCommandProvider.class);
	}
}
