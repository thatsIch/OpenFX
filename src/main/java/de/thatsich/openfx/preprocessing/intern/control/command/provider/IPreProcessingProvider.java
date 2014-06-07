package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.AANNPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.IdentityPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import org.encog.neural.networks.BasicNetwork;

public interface IPreProcessingProvider extends ICommandProvider
{
	AANNPreProcessing createAANNPreProcessing(BasicNetwork rebuildNetwork, PreProcessingConfig config);

	IdentityPreProcessing createIdentityPreProcessing(BasicNetwork rebuildNetwork, PreProcessingConfig config);
}

