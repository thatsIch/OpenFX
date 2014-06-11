package de.thatsich.openfx.preprocessing.intern.control.command.preprocessor;

import com.google.inject.Inject;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.APreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingProvider;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

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
	public ITrainedPreProcessor train(double[][] trainData, double[][] idealData, PreProcessingConfig config)
	{
		final int vectorLength = trainData[0].length;
		final BasicNetwork network = new BasicNetwork();

		final BasicLayer inputLayer = new BasicLayer(null, false, vectorLength);
		final BasicLayer outputLayer = new BasicLayer(null, false, vectorLength);

		network.addLayer(inputLayer);
		network.addLayer(outputLayer);

		network.getStructure().finalizeStructure();
		network.reset();

		final String name = config.name.get();
		final int inputSize = config.inputSize.get();
		final String id = config.id.get();
		final PreProcessingConfig newConfig = new PreProcessingConfig(name, inputSize, inputSize, id);

		return this.provider.createIdentityPreProcessing(network, newConfig);
	}
}
