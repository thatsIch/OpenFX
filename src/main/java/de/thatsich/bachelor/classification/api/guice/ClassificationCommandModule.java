package de.thatsich.bachelor.classification.api.guice;

import java.util.List;

import de.thatsich.bachelor.classification.intern.command.provider.IBinaryClassificationProvider;
import de.thatsich.bachelor.classification.intern.command.provider.IClassificationCommandProvider;
import de.thatsich.bachelor.classification.intern.command.provider.IClassificationInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


/**
 * Guice Module
 * @see ACommandModule
 * 
 * @author thatsIch
 */
public class ClassificationCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule( List<Class<? extends ICommandProvider>> providerList )
	{
		providerList.add( IClassificationCommandProvider.class );
		providerList.add( IBinaryClassificationProvider.class );
		providerList.add( IClassificationInitCommandProvider.class );
	}
}
