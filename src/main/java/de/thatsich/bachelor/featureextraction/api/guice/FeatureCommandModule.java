package de.thatsich.bachelor.featureextraction.api.guice;

import de.thatsich.bachelor.featureextraction.intern.command.IFeatureCommandProvider;
import de.thatsich.bachelor.featureextraction.intern.command.IFeatureInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

import java.util.List;


/**
 * Guice Module
 *
 * @author thatsIch
 * @see ACommandModule
 */
public class FeatureCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IFeatureCommandProvider.class);
		providerList.add(IFeatureInitCommandProvider.class);
	}
}
