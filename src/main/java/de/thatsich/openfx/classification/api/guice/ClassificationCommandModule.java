package de.thatsich.openfx.classification.api.guice;

import de.thatsich.openfx.classification.intern.control.provider.IBinaryClassificationProvider;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationInitCommandProvider;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;

import java.util.List;


/**
 * Guice Module
 *
 * @author thatsIch
 * @see ACommandModule
 */
public class ClassificationCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule(List<Class<? extends ICommandProvider>> providerList)
	{
		providerList.add(IClassificationCommandProvider.class);
		providerList.add(IBinaryClassificationProvider.class);
		providerList.add(IClassificationInitCommandProvider.class);
	}
}
