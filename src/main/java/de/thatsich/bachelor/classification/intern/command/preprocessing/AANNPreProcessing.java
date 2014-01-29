package de.thatsich.bachelor.classification.intern.command.preprocessing;

import org.opencv.core.MatOfFloat;

import de.thatsich.bachelor.classification.intern.command.preprocessing.core.APreProcessing;

/**
 * Result of a AANNPreProcessor
 * 
 * @author thatsIch
 */
public class AANNPreProcessing extends APreProcessing
{
	@Override
	public MatOfFloat preprocess( MatOfFloat featureVector )
	{
		return null;
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
