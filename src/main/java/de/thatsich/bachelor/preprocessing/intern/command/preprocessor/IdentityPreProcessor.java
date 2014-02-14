package de.thatsich.bachelor.preprocessing.intern.command.preprocessor;

import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.APreProcessor;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;

public class IdentityPreProcessor extends APreProcessor
{
	@Override
	public IPreProcessing train( double[][] trainData, double[][] idealData, PreProcessorConfiguration config )
	{
		return null;
	}
}
