package de.thatsich.openfx.taboverview.api.guice;

import de.thatsich.openfx.classification.api.guice.ClassificationCommandModule;
import de.thatsich.openfx.network.api.guice.NetworkCommandModule;
import de.thatsich.openfx.errorgeneration.api.guice.ErrorCommandModule;
import de.thatsich.openfx.featureextraction.api.guice.FeatureCommandModule;
import de.thatsich.openfx.imageprocessing.api.guice.ImageCommandModule;
import de.thatsich.openfx.prediction.api.guice.PredictionCommandModule;
import de.thatsich.openfx.preprocessing.api.guice.PreProcessingCommandModule;
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
