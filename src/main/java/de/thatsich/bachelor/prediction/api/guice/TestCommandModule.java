package de.thatsich.bachelor.prediction.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.prediction.intern.command.BinaryPredictionCommandProvider;

public class TestCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(BinaryPredictionCommandProvider.class)
		);
	}
}
