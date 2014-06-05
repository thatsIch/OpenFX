package de.thatsich.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract Guice Module
 *
 * Build factories to provide the assisted
 * constructions and install the factory-modules
 *
 * @author thatsIch
 */
public abstract class ACommandModule extends AbstractModule
{

	@Override
	protected final void configure()
	{
		final List<Class<? extends ICommandProvider>> additionalProvider = new ArrayList<>();
		this.buildProviderModule(additionalProvider);

		for (Class<? extends ICommandProvider> providerClass : additionalProvider)
		{
			this.install(new FactoryModuleBuilder().build(providerClass));
		}
	}

	/**
	 * Abstract Method
	 *
	 * Child needs to implement this and add its {@link ICommandProvider} to the
	 * get
	 *
	 * @param providerList List of ICommandProvider
	 */
	protected abstract void buildProviderModule(List<Class<? extends ICommandProvider>> providerList);
}
