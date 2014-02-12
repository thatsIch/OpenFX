package de.thatsich.bachelor.classification.intern.command.provider;

import de.thatsich.bachelor.classification.intern.command.preprocessing.AANNPreProcessor;
import de.thatsich.core.guice.ICommandProvider;

public interface IPreProcessingProvider extends ICommandProvider
{
	AANNPreProcessor createAANNPreProcessor();
}
