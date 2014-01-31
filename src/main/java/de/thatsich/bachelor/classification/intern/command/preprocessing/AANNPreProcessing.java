package de.thatsich.bachelor.classification.intern.command.preprocessing;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

import de.thatsich.bachelor.classification.intern.command.preprocessing.core.APreProcessing;


/**
 * Result of a AANNPreProcessor
 * 
 * @author thatsIch
 */
public class AANNPreProcessing extends APreProcessing
{
	private final BasicNetwork	network;

	public AANNPreProcessing( BasicNetwork network )
	{
		this.network = network;
	}

	@Override
	public double[] preprocess( double[] featureVector )
	{
		final MLData inputData = new BasicMLData( featureVector );
		final MLData outputData = this.network.compute( inputData );

		return outputData.getData();
	}

	@Override
	public void load( String fileName )
	{
	}

	@Override
	public void save( String fileName )
	{
	}

}
