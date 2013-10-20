package de.thatsich.bachelor.classificationtesting.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.classificationtesting.restricted.services.TestCommandService;

public class TestCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(TestCommandService.class)
		);
	}
}
