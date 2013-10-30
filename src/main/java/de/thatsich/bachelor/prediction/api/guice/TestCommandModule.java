package de.thatsich.bachelor.prediction.api.guice;

import java.util.List;

import de.thatsich.bachelor.prediction.intern.command.BinaryPredictionCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

public class TestCommandModule extends ACommandModule {
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList) {
		providerList.add(BinaryPredictionCommandProvider.class);
	}
}
