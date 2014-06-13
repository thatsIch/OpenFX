package de.thatsich.openfx.featureextraction.api.guice;

import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.provider.IFeatureCommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.provider.IFeatureInitCommandProvider;

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
