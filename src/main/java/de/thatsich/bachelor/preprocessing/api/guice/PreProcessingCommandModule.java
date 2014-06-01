package de.thatsich.bachelor.preprocessing.api.guice;

import de.thatsich.bachelor.preprocessing.intern.control.command.provider.IPreProcessingCommandProvider;
import de.thatsich.bachelor.preprocessing.intern.control.command.provider.IPreProcessingInitCommandProvider;
import de.thatsich.bachelor.preprocessing.intern.control.command.provider.IPreProcessingProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

import java.util.List;


public class PreProcessingCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IPreProcessingCommandProvider.class);
		providerList.add(IPreProcessingProvider.class);
		providerList.add(IPreProcessingInitCommandProvider.class);
	}
}
