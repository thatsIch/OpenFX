package de.thatsich.bachelor.errorgeneration.api.guice;

import java.util.List;

import de.thatsich.bachelor.errorgeneration.restricted.command.ErrorCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


public class ErrorCommandModule extends ACommandModule {
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList) {
		providerList.add(ErrorCommandProvider.class);
	}
}
