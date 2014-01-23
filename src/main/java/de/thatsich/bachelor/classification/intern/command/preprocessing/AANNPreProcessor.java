package de.thatsich.bachelor.classification.intern.command.preprocessing;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;


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

		return network;
	}

	public void train( double[][] input, double[][] ideal )
	{

		// create training data
		final MLDataSet trainingSet = new BasicMLDataSet( input, ideal );

		// train neural network
		final ResilientPropagation train = new ResilientPropagation( this.network, trainingSet );

		int epoch = 1;

		do
		{
			train.iteration();
			System.out.println( "Epoch #" + epoch + " Error:" + train.getError() );
			epoch++;
		}
		while ( train.getError() > 0.01 );
		train.finishTraining();
	}

	public void test()
	{
//		// test the neural network
//		System.out.println( "Neural Network Results:" );
//		for ( MLDataPair pair : trainingSet )
//		{
//			final MLData output = this.network.compute( pair.getInput() );
//			System.out.println( pair.getInput().getData( 0 ) + "," + pair.getInput().getData( 1 ) + ", actual=" + output.getData( 0 ) + ",ideal=" + pair.getIdeal().getData( 0 ) );
//		}

		Encog.getInstance().shutdown();
	}
}
