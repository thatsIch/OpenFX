package de.thatsich.openfx.preprocessed.api.guice;

import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessed.intern.control.command.provider.IPreProcessedCommandProvider;
import de.thatsich.openfx.preprocessed.intern.control.command.provider.IPreProcessedInitCommandProvider;

import java.util.List;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public class PreProcessedCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IPreProcessedCommandProvider.class);
		providerList.add(IPreProcessedInitCommandProvider.class);
	}
}
