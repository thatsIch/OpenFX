package de.thatsich.bachelor.classification.intern.command.preprocessing;

import java.security.InvalidParameterException;

import javafx.util.Pair;

import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import de.thatsich.bachelor.classification.intern.command.preprocessing.core.APreProcessor;
import de.thatsich.bachelor.classification.intern.command.preprocessing.core.IPreProcessing;


public class AANNPreProcessor extends APreProcessor
{
	private final static int	AVERAGE_ITERATION_COUNT	= 10;
	private final static double	MAX_ERROR				= 0.01;
	private final static int	MAX_ITERATION			= 1000;

	// public void test( double[][] input, double[][] ideal )
	// {
	// // create training data
	// final MLDataSet trainingSet = new BasicMLDataSet( input, ideal );
	//
	// System.out.println( "Neural Network Results:" );
	// for ( MLDataPair pair : trainingSet )
	// {
	// final MLData output = network.compute( pair.getInput() );
	// System.out.println( pair.getInput().getData( 0 ) + "," +
	// pair.getInput().getData( 1 ) + ", actual=" + output.getData( 0 ) +
	// ",ideal=" + pair.getIdeal().getData( 0 ) );
	// }
	//
	// for ( int layer = 0; layer < network.getLayerCount() - 1; layer++ )
	// {
	// System.out.println( "Layer " + layer );
	// for ( int neuron = 0; neuron < network.getLayerNeuronCount( layer );
	// neuron++ )
	// {
	// for ( int next = 0; next < network.getLayerNeuronCount( layer + 1 );
	// next++ )
	// {
	// System.out.println( "From " + neuron + " to " + next + ": " +
	// network.getWeight( layer, neuron, next ) );
	// }
	// }
	// }
	//
	// Encog.getInstance().shutdown();
	// }

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public IPreProcessing train( double[][] trainData, double[][] labelData )
	{
		// extract important informations
		final int featureVectorCount = trainData.length;
		final int featureVectorLength = trainData[0].length;

		final int minHiddenLayerSize = featureVectorLength + 1;
		final int maxHiddenLayerSize = featureVectorLength * 2;
		final int minBottleLayerSize = 1;
		final int maxBottleLayerSize = featureVectorLength;

		// validate
		if ( featureVectorCount < 2 ) throw new InvalidParameterException( "Not enough FeatureVectors to Cross-Validate the input data." );
		if ( featureVectorLength < 1 ) throw new InvalidParameterException( "FeatureVector Size is 0." );
		if ( maxHiddenLayerSize < minHiddenLayerSize ) throw new InvalidParameterException( "The maxHiddenLayerSize is smaller than the minHiddenLayerSize." );
		if ( maxBottleLayerSize < minBottleLayerSize ) throw new InvalidParameterException( "The maxBottleLayerSize is smaller than the minBottleLayerSize." );

		// create real train data set
		final MLDataSet trainingSet = new BasicMLDataSet( trainData, labelData );
		final FoldedDataSet foldedSet = new FoldedDataSet( trainingSet );

		// save best setup
		Pair<BasicNetwork, Double> bestSetup = new Pair<>( null, Double.MAX_VALUE );

		// try out every possible hidden and bottleneck combination
		for ( int hiddenLayerSize = minHiddenLayerSize; hiddenLayerSize < maxHiddenLayerSize; hiddenLayerSize++ )
		{
			for ( int bottleLayerSize = minBottleLayerSize; bottleLayerSize < maxBottleLayerSize; bottleLayerSize++ )
			{
				for ( int averageIteration = 0; averageIteration < AVERAGE_ITERATION_COUNT; averageIteration++ )
				{
					// create network
					final BasicNetwork network = this.setupNetwork( featureVectorLength, hiddenLayerSize, bottleLayerSize );
					// final MLTrain trainer = new LevenbergMarquardtTraining(
					// network, foldedSet );
					final MLTrain trainer = new ResilientPropagation( network, foldedSet );
					final CrossValidationKFold trainFolded = new CrossValidationKFold( trainer, foldedSet.getIdealSize() );
					final double trainedError = this.trainFold( trainFolded );

					if ( trainedError < bestSetup.getValue() )
					{
						bestSetup = new Pair<>( network, trainedError );
					}
				}
			}
		}

		this.log.info( "Best Network " + bestSetup.getKey().getLayerTotalNeuronCount( 1 ) + "," + bestSetup.getKey().getLayerTotalNeuronCount( 2 ) + " with Error " + bestSetup.getValue() );

		// build a new network based on the trained one with only 3 Layers to
		// reduce the dimension
		final BasicNetwork rebuildNetwork = this.rebuildNetworkForDimensionReduction( bestSetup.getKey() );

		return new AANNPreProcessing( rebuildNetwork );
	}

	/**
	 * TODO 
	 * @param network
	 * @return
	 */
	BasicNetwork rebuildNetworkForDimensionReduction( BasicNetwork network )
	{
		return network;
	}

	BasicNetwork setupNetwork( int inputSize, int hiddenSize, int bottleneckSize )
	{

		final BasicNetwork network = new BasicNetwork();

		// InputLayer
		network.addLayer( new BasicLayer( null, false, inputSize ) );

		// CompressionLayer
		network.addLayer( new BasicLayer( new ActivationSigmoid(), true, hiddenSize ) );

		// BottleneckLayer
		network.addLayer( new BasicLayer( new ActivationLinear(), true, hiddenSize ) );

		// DecompressionLayer
		network.addLayer( new BasicLayer( new ActivationSigmoid(), true, hiddenSize ) );

		// OutputLayer
		network.addLayer( new BasicLayer( null, false, inputSize ) );

		// finalize network
		network.getStructure().finalizeStructure();
		network.reset();

		this.log.info( "Network setup: " + inputSize + ", " + hiddenSize + ", " + bottleneckSize );

		return network;
	}

	double trainFold( CrossValidationKFold trainFolded )
	{
		do
		{
			trainFolded.preIteration();
			trainFolded.iteration();
		}
		while ( trainFolded.getError() > MAX_ERROR && trainFolded.getIteration() < MAX_ITERATION );

		this.log.info( "Epoch #" + trainFolded.getIteration() + " Error:" + trainFolded.getError() );

		trainFolded.finishTraining();

		return trainFolded.getError();
	}
}
