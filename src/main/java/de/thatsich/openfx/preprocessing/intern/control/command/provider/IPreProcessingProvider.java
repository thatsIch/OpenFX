package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.AANNTrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.IdentityTrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.TrainedPreProcessorConfig;
import org.encog.neural.networks.BasicNetwork;

public interface IPreProcessingProvider extends ICommandProvider
{
	AANNTrainedPreProcessor createAANNPreProcessing(BasicNetwork rebuildNetwork, TrainedPreProcessorConfig config);

	IdentityTrainedPreProcessor createIdentityPreProcessing(BasicNetwork rebuildNetwork, TrainedPreProcessorConfig config);
}

