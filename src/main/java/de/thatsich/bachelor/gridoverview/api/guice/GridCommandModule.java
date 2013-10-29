package de.thatsich.bachelor.gridoverview.api.guice;

import java.util.List;

import de.thatsich.bachelor.classification.api.guice.TrainCommandModule;
import de.thatsich.bachelor.classificationtesting.restricted.app.guice.TestCommandModule;
import de.thatsich.bachelor.errorgeneration.api.guice.ErrorCommandModule;
import de.thatsich.bachelor.featureextraction.api.guice.FeatureCommandModule;
import de.thatsich.bachelor.imageprocessing.api.guice.ImageCommandModule;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

public class GridCommandModule extends ACommandModule {

	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList) {
		this.install(new TestCommandModule());
		this.install(new TrainCommandModule());
		this.install(new ErrorCommandModule());
		this.install(new FeatureCommandModule());
		this.install(new ImageCommandModule());
	}
}
