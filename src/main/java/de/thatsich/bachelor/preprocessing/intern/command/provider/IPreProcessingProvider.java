package de.thatsich.bachelor.preprocessing.intern.command.provider;

import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.core.guice.ICommandProvider;

public interface IPreProcessingProvider extends ICommandProvider
{
	IPreProcessing createAANNPreProcessing( PreProcessorConfiguration config );
}

