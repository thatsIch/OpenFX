package de.thatsich.bachelor.featureextraction.api.guice;

import java.util.List;

import de.thatsich.bachelor.featureextraction.restricted.command.IFeatureCommandProvider;
import de.thatsich.bachelor.featureextraction.restricted.command.IFeatureInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


public class FeatureCommandModule extends ACommandModule {
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList) {
		providerList.add(IFeatureCommandProvider.class);
		providerList.add(IFeatureInitCommandProvider.class);
	}
}
