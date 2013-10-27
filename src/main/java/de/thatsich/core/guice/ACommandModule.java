package de.thatsich.core.guice;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public abstract class ACommandModule extends AbstractModule {

	@Override
	protected final void configure() {
		final List<Class<? extends ICommandProvider>> additionalProvider = new ArrayList<Class<? extends ICommandProvider>>();
		this.bind(additionalProvider);
		
		for (Class<? extends ICommandProvider> providerClass : additionalProvider) {
			this.install(new FactoryModuleBuilder().build(providerClass));
		}
	}

	protected abstract void bind(List<Class<? extends ICommandProvider>> providerList);
}
