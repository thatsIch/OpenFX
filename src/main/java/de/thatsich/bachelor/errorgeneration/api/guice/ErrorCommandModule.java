package de.thatsich.bachelor.errorgeneration.api.guice;

import java.util.List;

import de.thatsich.bachelor.errorgeneration.intern.command.IErrorCommandProvider;
import de.thatsich.bachelor.errorgeneration.intern.command.IErrorInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


/**
 * Guice Module
 * 
 * @see ACommandModule
 * 
 * @author thatsIch
 */
public class ErrorCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule( List<Class<? extends ICommandProvider>> providerList )
	{
		providerList.add( IErrorCommandProvider.class );
		providerList.add( IErrorInitCommandProvider.class );
	}
}
