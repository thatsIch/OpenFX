package de.thatsich.bachelor.classification.api.guice;

import java.util.List;

import de.thatsich.bachelor.classification.intern.command.BinaryClassificationProvider;
import de.thatsich.bachelor.classification.intern.command.ClassificationCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


public class TrainCommandModule extends ACommandModule {
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList) {
		providerList.add(ClassificationCommandProvider.class);
		providerList.add(BinaryClassificationProvider.class);
	}
}
