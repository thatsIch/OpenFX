package de.thatsich.bachelor.imageprocessing.api.guice;

import java.util.List;

import de.thatsich.bachelor.imageprocessing.restricted.command.IImageCommandProvider;
import de.thatsich.bachelor.imageprocessing.restricted.command.IImageInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


public class ImageCommandModule extends ACommandModule {
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList) {
		providerList.add(IImageCommandProvider.class);
		providerList.add(IImageInitCommandProvider.class);
	}
}
