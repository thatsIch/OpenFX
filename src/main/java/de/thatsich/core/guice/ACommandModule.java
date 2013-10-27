package de.thatsich.core.guice;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

public abstract class ACommandModule extends AbstractModule {

	@Override
	protected void configure() {
		final List<Module> additionalModules = new ArrayList<>();
		this.install(additionalModules);
		
		for (Module m : additionalModules) {
			this.install(m);
		}
	}

	protected abstract void install(List<Module> moduleList);
}
