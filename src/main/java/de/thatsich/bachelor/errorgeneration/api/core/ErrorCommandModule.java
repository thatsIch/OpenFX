package de.thatsich.bachelor.errorgeneration.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorCommandService;


public class ErrorCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(ErrorCommandService.class)
		);
	}
}
