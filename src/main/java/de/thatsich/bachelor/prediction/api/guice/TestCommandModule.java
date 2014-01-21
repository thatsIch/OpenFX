package de.thatsich.bachelor.prediction.api.guice;

import java.util.List;

import de.thatsich.bachelor.prediction.intern.command.provider.IPredictionCommandProvider;
import de.thatsich.bachelor.prediction.intern.command.provider.IPredictionInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

/**
 * Guice Module
 * 
 * @see ACommandModule
 * 
 * @author thatsIch
 */
public class TestCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule( List<Class<? extends ICommandProvider>> providerList )
	{
		providerList.add( IPredictionCommandProvider.class );
		providerList.add( IPredictionInitCommandProvider.class );
	}
}
