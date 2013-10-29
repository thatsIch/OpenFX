package de.thatsich.bachelor.featureextraction.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.featureextraction.restricted.application.guice.FeatureCommandProvider;


public class FeatureCommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(FeatureCommandProvider.class)
		);
	}
}
