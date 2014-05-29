package de.thatsich.bachelor.preprocessing.intern.command.preprocessor;

import com.google.inject.Inject;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.APreProcessor;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.bachelor.preprocessing.intern.command.provider.IPreProcessingProvider;
import org.encog.neural.networks.BasicNetwork;

public class IdentityPreProcessor extends APreProcessor
{
	@Inject
	private IPreProcessingProvider provider;

	@Override
	public IPreProcessing train(double[][] trainData, double[][] idealData, PreProcessorConfiguration config)
	{
		return this.provider.createIdentityPreProcessing(new BasicNetwork(), config);
	}
}
