package de.thatsich.bachelor.classificationtesting.restricted.app.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TestCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(BinaryPredictionCommandProvider.class)
		);
	}
}