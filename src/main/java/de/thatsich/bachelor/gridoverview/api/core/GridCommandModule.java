package de.thatsich.bachelor.gridoverview.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.classificationtesting.restricted.app.guice.TestCommandModule;
import de.thatsich.bachelor.classificationtraining.api.core.TrainCommandModule;
import de.thatsich.bachelor.errorgeneration.api.core.ErrorCommandModule;
import de.thatsich.bachelor.featureextraction.api.core.FeatureCommandModule;
import de.thatsich.bachelor.gridoverview.restricted.services.GridCommandService;
import de.thatsich.bachelor.imageprocessing.api.core.ImageCommandModule;

public class GridCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(GridCommandService.class)
		);
		
		this.install(new TestCommandModule());
		this.install(new TrainCommandModule());
		this.install(new ErrorCommandModule());
		this.install(new FeatureCommandModule());
		this.install(new ImageCommandModule());
	}
}
