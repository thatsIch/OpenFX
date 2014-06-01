package de.thatsich.bachelor.taboverview.api.guice;

import de.thatsich.bachelor.classification.api.guice.ClassificationCommandModule;
import de.thatsich.bachelor.network.api.guice.NetworkCommandModule;
import de.thatsich.bachelor.errorgeneration.api.guice.ErrorCommandModule;
import de.thatsich.bachelor.featureextraction.api.guice.FeatureCommandModule;
import de.thatsich.bachelor.imageprocessing.api.guice.ImageCommandModule;
import de.thatsich.bachelor.prediction.api.guice.PredictionCommandModule;
import de.thatsich.bachelor.preprocessing.api.guice.PreProcessingCommandModule;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

import java.util.List;

public class TabOverviewCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		this.install(new NetworkCommandModule());
		this.install(new PredictionCommandModule());
		this.install(new ClassificationCommandModule());
		this.install(new ErrorCommandModule());
		this.install(new PreProcessingCommandModule());
		this.install(new FeatureCommandModule());
		this.install(new ImageCommandModule());
	}
}
