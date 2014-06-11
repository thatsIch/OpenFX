package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.AANNTrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.IdentityTrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import org.encog.neural.networks.BasicNetwork;

public interface IPreProcessingProvider extends ICommandProvider
{
	AANNTrainedPreProcessor createAANNPreProcessing(BasicNetwork rebuildNetwork, PreProcessingConfig config);

	IdentityTrainedPreProcessor createIdentityPreProcessing(BasicNetwork rebuildNetwork, PreProcessingConfig config);
}

