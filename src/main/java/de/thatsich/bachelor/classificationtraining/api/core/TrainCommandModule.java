package de.thatsich.bachelor.classificationtraining.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.classificationtraining.restricted.application.guice.BinaryClassificationProvider;
import de.thatsich.bachelor.classificationtraining.restricted.application.guice.TrainCommandProvider;


public class TrainCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(new FactoryModuleBuilder().build(TrainCommandProvider.class));
		this.install(new FactoryModuleBuilder().build(BinaryClassificationProvider.class));
	}
}
