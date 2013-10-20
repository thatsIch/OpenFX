package de.thatsich.bachelor.featureextraction.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureCommandService;


public class FeatureCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(FeatureCommandService.class)
		);
	}
}
