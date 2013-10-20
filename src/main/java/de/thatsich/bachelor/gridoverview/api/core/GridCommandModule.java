package de.thatsich.bachelor.gridoverview.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.gridoverview.restricted.services.GridCommandService;

public class GridCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(GridCommandService.class)
		);
	}
}
