package de.thatsich.bachelor.classification.intern.command.preprocessing;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.strategy.end.EarlyStoppingStrategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;

import de.thatsich.bachelor.classification.intern.command.classifier.core.ABinaryClassifier;


public class AANNPreProcessor
{
	private final BasicNetwork	network;

	public AANNPreProcessor( int inputSize, int hiddenSize, int bottleneckSize )
	{
		this.network = this.setupNetwork( inputSize, hiddenSize, bottleneckSize );
	}

	private BasicNetwork setupNetwork( int inputSize, int hiddenSize, int bottleneckSize )
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

		return network;
	}

	public void train( double[][] input, double[][] ideal )
	{
		// create training data
		final MLDataSet trainingSet = new BasicMLDataSet( input, ideal );

		// train neural network
		// final Backpropagation train = new Backpropagation( network,
		// trainingSet );
		final LevenbergMarquardtTraining train = new LevenbergMarquardtTraining( network, trainingSet );
		// final ResilientPropagation train = new ResilientPropagation( network,
		// trainingSet );
		// final LevenbergMarquardtTraining train = new
		// LevenbergMarquardtTraining( this.network, trainingSet );
		// train.setRPROPType( RPROPType.iRPROPp );
		final EarlyStoppingStrategy strategy = new EarlyStoppingStrategy( trainingSet, trainingSet );
		// final EndMaxErrorStrategy strategy = new EndMaxErrorStrategy( 0.01 );
		train.addStrategy( strategy );

		int epoch = 1;
		do
		{
			train.iteration();
			System.out.println( "Epoch #" + epoch + " Error:" + train.getError() );
			epoch++;

			if ( strategy.shouldStop() )
			{
				System.out.println( "Converged" );
				return;
			}
			if ( epoch > 1000 )
			{
				System.out.println( "too many epochs" );
				return;
			}
		}
		while ( train.getError() > 0.01 );

		train.finishTraining();
	}

	public void test( double[][] input, double[][] ideal )
	{
		// create training data
		final MLDataSet trainingSet = new BasicMLDataSet( input, ideal );

		System.out.println( "Neural Network Results:" );
		for ( MLDataPair pair : trainingSet )
		{
			final MLData output = network.compute( pair.getInput() );
			System.out.println( pair.getInput().getData( 0 ) + "," + pair.getInput().getData( 1 ) + ", actual=" + output.getData( 0 ) + ",ideal=" + pair.getIdeal().getData( 0 ) );
		}

		for ( int layer = 0; layer < network.getLayerCount() - 1; layer++ )
		{
			System.out.println( "Layer " + layer );
			for ( int neuron = 0; neuron < network.getLayerNeuronCount( layer ); neuron++ )
			{
				for ( int next = 0; next < network.getLayerNeuronCount( layer + 1 ); next++ )
				{
					System.out.println( "From " + neuron + " to " + next + ": " + network.getWeight( layer, neuron, next ) );
				}
			}
		}

		Encog.getInstance().shutdown();
	}
}
