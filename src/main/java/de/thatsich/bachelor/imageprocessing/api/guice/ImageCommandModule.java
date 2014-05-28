package de.thatsich.bachelor.imageprocessing.api.guice;

import de.thatsich.bachelor.imageprocessing.intern.command.provider.IImageCommandProvider;
import de.thatsich.bachelor.imageprocessing.intern.command.provider.IImageInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

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
