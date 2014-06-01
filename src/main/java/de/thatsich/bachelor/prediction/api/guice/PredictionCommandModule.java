package de.thatsich.bachelor.prediction.api.guice;

import de.thatsich.bachelor.prediction.intern.control.provider.IPredictionCommandProvider;
import de.thatsich.bachelor.prediction.intern.control.provider.IPredictionInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

import java.util.List;

/**
 * Guice Module
 *
 * @author thatsIch
 * @see ACommandModule
 */
public class PredictionCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IPredictionCommandProvider.class);
		providerList.add(IPredictionInitCommandProvider.class);
	}
}
