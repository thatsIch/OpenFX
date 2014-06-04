package de.thatsich.openfx.preprocessing.intern.control.command.preprocessor;

import com.google.inject.Inject;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.APreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingProvider;
import org.encog.neural.networks.BasicNetwork;

public class IdentityPreProcessor extends APreProcessor
{
	@Inject
	private IPreProcessingProvider provider;

	@Override
	public String toString()
	{
		return "IdentityPreProcessor";
	}

	@Override
	public IPreProcessing train(double[][] trainData, double[][] idealData, PreProcessorConfiguration config)
	{
		return this.provider.createIdentityPreProcessing(new BasicNetwork(), config);
	}
}
