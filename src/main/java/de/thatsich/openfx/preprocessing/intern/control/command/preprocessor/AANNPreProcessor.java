package de.thatsich.openfx.preprocessing.intern.control.command.preprocessor;

import com.google.inject.Inject;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.TrainedPreProcessorConfig;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.APreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingProvider;
import javafx.util.Pair;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.propagation.resilient.RPROPType;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import java.security.InvalidParameterException;


/**
 * Auto Associative Neural Network
 * PreProcessor
 *
 * Using a AANN to reduce dimensionality of TestData.
 * Using iRPROP+ to train the network.
 *
 * Automatic detection for KFoldCrossValidation.
 *
 * @author thatsIch
 */
public class AANNPreProcessor extends APreProcessor
{
	// Consts
	private final static int AVERAGE_ITERATION_COUNT = 10;
	private final static double MAX_ERROR = 0.01;
	private final static int MAX_ITERATION = 1000;
	// Injects
	@Inject
	private IPreProcessingProvider provider;

	@Override
	public String toString()
	{
		return "AANNPreProcessor";
	}

	@Override
	public ITrainedPreProcessor train(double[][] trainData, double[][] labelData, TrainedPreProcessorConfig config)
	{
		// extract important information
		final int featureVectorCount = trainData.length;
		final int featureVectorLength = trainData[0].length;

		final int minHiddenLayerSize = featureVectorLength + 1;
		final int maxHiddenLayerSize = featureVectorLength * 2;
		final int minBottleLayerSize = 1;
		final int maxBottleLayerSize = featureVectorLength;

		this.validate(featureVectorCount, featureVectorLength, minHiddenLayerSize, maxHiddenLayerSize, minBottleLayerSize, maxBottleLayerSize);

		// create real train data set
		final MLDataSet trainingSet = new BasicMLDataSet(trainData, labelData);
		final FoldedDataSet foldedSet = new FoldedDataSet(trainingSet);

		// create best setup
		Pair<BasicNetwork, Double> bestSetup = new Pair<>(null, Double.MAX_VALUE);

		// try out every possible hidden and bottleneck combination
		for (int hiddenLayerSize = minHiddenLayerSize; hiddenLayerSize < maxHiddenLayerSize; hiddenLayerSize++)
		{
			for (int bottleLayerSize = minBottleLayerSize; bottleLayerSize < maxBottleLayerSize; bottleLayerSize++)
			{
				for (int averageIteration = 0; averageIteration < AVERAGE_ITERATION_COUNT; averageIteration++)
				{
					// create network
					final BasicNetwork network = this.setupNetwork(featureVectorLength, hiddenLayerSize, bottleLayerSize);
					final ResilientPropagation trainer = new ResilientPropagation(network, foldedSet);
					trainer.setRPROPType(RPROPType.iRPROPp);
					final CrossValidationKFold trainFolded = new CrossValidationKFold(trainer, foldedSet.getIdealSize());
					final double trainedError = this.trainFold(trainFolded);

					// store network if error was better
					if (trainedError < bestSetup.getValue())
					{
						bestSetup = new Pair<>(network, trainedError);
					}
				}
			}
		}

		this.log.info("Best Network with Error " + bestSetup.getValue());

		// build a new network based on the trained one with only 3 Layers to
		// reduce the dimension
		final BasicNetwork rebuildNetwork = this.rebuildNetworkForDimensionReduction(bestSetup.getKey());

		// rebuild config
		final String preProcessorName = config.name.get();
		final int inputSize = rebuildNetwork.getInputCount();
		final int outputSize = rebuildNetwork.getOutputCount();
		final String id = config.id.get();

		final TrainedPreProcessorConfig newerConfig = new TrainedPreProcessorConfig(preProcessorName, inputSize, outputSize, id);
		return this.provider.createAANNPreProcessing(rebuildNetwork, newerConfig);
	}

	private void validate(int featureVectorCount, int featureVectorLength, int minHiddenLayerSize, int maxHiddenLayerSize, int minBottleLayerSize, int maxBottleLayerSize)
	{
		if (featureVectorCount < 2)
		{
			throw new InvalidParameterException("Not enough FeatureVectors to Cross-Validate the input data.");
		}
		if (featureVectorLength < 1)
		{
			throw new InvalidParameterException("FeatureVector Size is 0.");
		}
		if (maxHiddenLayerSize < minHiddenLayerSize)
		{
			throw new InvalidParameterException("The maxHiddenLayerSize is smaller than the minHiddenLayerSize.");
		}
		if (maxBottleLayerSize < minBottleLayerSize)
		{
			throw new InvalidParameterException("The maxBottleLayerSize is smaller than the minBottleLayerSize.");
		}
	}

	/**
	 * Creates an AANN based on Size of the input vector, size of the hidden
	 * layer and size of the bottleneck layer
	 *
	 * @param inputSize      Size of the InputLayer
	 * @param hiddenSize     Size of the HiddenLayer
	 * @param bottleneckSize Size of the BottleNeckLayer
	 *
	 * @return AANN
	 */
	BasicNetwork setupNetwork(int inputSize, int hiddenSize, int bottleneckSize)
	{
		final BasicNetwork network = new BasicNetwork();
		final BasicLayer inputLayer = new BasicLayer(null, false, inputSize);
		final BasicLayer compressionLayer = new BasicLayer(new ActivationSigmoid(), true, hiddenSize);
		final BasicLayer bottleneckLayer = new BasicLayer(new ActivationLinear(), true, bottleneckSize);
		final BasicLayer decompressionLayer = new BasicLayer(new ActivationSigmoid(), true, hiddenSize);
		final BasicLayer outputLayer = new BasicLayer(null, false, inputSize);

		// set layers
		network.addLayer(inputLayer);
		network.addLayer(compressionLayer);
		network.addLayer(bottleneckLayer);
		network.addLayer(decompressionLayer);
		network.addLayer(outputLayer);

		// finalize network
		network.getStructure().finalizeStructure();
		network.reset();

		this.log.info("Network setup: " + inputSize + ", " + hiddenSize + ", " + bottleneckSize);

		return network;
	}

	/**
	 * Trains the CrossValidation KFold for MAX_ERROR and MAX_ITERATION
	 *
	 * @param trainFolded To be trained Fold
	 *
	 * @return The trained Error
	 */
	double trainFold(CrossValidationKFold trainFolded)
	{
		do
		{
			trainFolded.preIteration();
			trainFolded.iteration();
		} while (trainFolded.getError() > MAX_ERROR && trainFolded.getIteration() < MAX_ITERATION);

		this.log.info("Epoch #" + trainFolded.getIteration() + " Error:" + trainFolded.getError());

		trainFolded.finishTraining();

		return trainFolded.getError();
	}

	/**
	 * extracts the values from the learned AANN
	 * and uses the Compression Network to reduce
	 * dimensionality
	 *
	 * @param network To be extractec Network
	 *
	 * @return Compression Network
	 */
	BasicNetwork rebuildNetworkForDimensionReduction(BasicNetwork network)
	{
		// get Layer count
		final int inputSize = network.getLayerNeuronCount(0);
		final int hiddenSize = network.getLayerNeuronCount(1);
		final int bottleSize = network.getLayerNeuronCount(2);

		this.log.info("Resulting Structure: " + inputSize + ", " + hiddenSize + ", " + bottleSize);

		// build AANN result network
		final BasicNetwork rebuild = new BasicNetwork();
		rebuild.addLayer(new BasicLayer(null, false, inputSize));
		rebuild.addLayer(new BasicLayer(new ActivationSigmoid(), true, hiddenSize));
		rebuild.addLayer(new BasicLayer(new ActivationLinear(), true, bottleSize));

		rebuild.getStructure().finalizeStructure();
		rebuild.reset();

		// copy weights from Input to Hidden Layer
		for (int input = 0; input < inputSize; input++)
		{
			for (int hidden = 0; hidden < hiddenSize; hidden++)
			{
				final double weight = network.getWeight(0, input, hidden);
				rebuild.setWeight(0, input, hidden, weight);
			}
		}

		// copy weights from Hidden to Bottle Layer
		for (int hidden = 0; hidden < hiddenSize; hidden++)
		{
			for (int bottle = 0; bottle < bottleSize; bottle++)
			{
				final double weight = network.getWeight(1, hidden, bottle);
				rebuild.setWeight(1, hidden, bottle, weight);
			}
		}

		return rebuild;
	}
}
