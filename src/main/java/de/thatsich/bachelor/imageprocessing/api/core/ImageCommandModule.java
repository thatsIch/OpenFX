package de.thatsich.bachelor.imageprocessing.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.imageprocessing.restricted.services.ImageCommandService;


public class ImageCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(ImageCommandService.class)
		);
	}
}
