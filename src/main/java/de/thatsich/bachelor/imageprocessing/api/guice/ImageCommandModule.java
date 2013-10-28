package de.thatsich.bachelor.imageprocessing.api.guice;

import java.util.List;

import de.thatsich.bachelor.imageprocessing.restricted.command.ImageCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


public class ImageCommandModule extends ACommandModule {
	@Override
	protected void bind(List<Class<? extends ICommandProvider>> providerList) {
		providerList.add(ImageCommandProvider.class);
	}
}
