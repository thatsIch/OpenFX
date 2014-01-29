package de.thatsich.bachelor.classification.intern.command.preprocessing;

import org.encog.neural.networks.BasicNetwork;

import de.thatsich.bachelor.classification.intern.command.preprocessing.core.APreProcessing;

/**
 * Result of a AANNPreProcessor
 * 
 * @author thatsIch
 */
public class AANNPreProcessing extends APreProcessing
{
	private final BasicNetwork network;
	private final int outputSize;
	
	public AANNPreProcessing(BasicNetwork network) {
		this.network = network;
		this.outputSize = 0; // TODO
	}
	
	@Override
	public double[] preprocess( double[] featureVector )
	{
		final double[] output = new double[this.outputSize];
		this.network.compute( featureVector, output );
		
		return output;
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
