package de.thatsich.bachelor.preprocessing.intern.command.provider;

import de.thatsich.bachelor.preprocessing.intern.command.preprocessing.AANNPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessing.IdentityPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.core.guice.ICommandProvider;
import org.encog.neural.networks.BasicNetwork;

public interface IPreProcessingProvider extends ICommandProvider
{
	AANNPreProcessing createAANNPreProcessing(BasicNetwork rebuildNetwork, PreProcessorConfiguration config);
	IdentityPreProcessing createIdentityPreProcessing(BasicNetwork rebuildNetwork, PreProcessorConfiguration config);
}

