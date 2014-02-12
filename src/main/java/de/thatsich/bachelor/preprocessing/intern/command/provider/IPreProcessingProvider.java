package de.thatsich.bachelor.preprocessing.intern.command.provider;

import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.AANNPreProcessor;
import de.thatsich.core.guice.ICommandProvider;

public interface IPreProcessingProvider extends ICommandProvider
{
	AANNPreProcessor createAANNPreProcessor();
}

