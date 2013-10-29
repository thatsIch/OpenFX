package de.thatsich.bachelor.classification.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.classification.intern.command.BinaryClassificationProvider;
import de.thatsich.bachelor.classification.intern.command.TrainCommandProvider;


public class TrainCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(new FactoryModuleBuilder().build(TrainCommandProvider.class));
		this.install(new FactoryModuleBuilder().build(BinaryClassificationProvider.class));
	}
}
