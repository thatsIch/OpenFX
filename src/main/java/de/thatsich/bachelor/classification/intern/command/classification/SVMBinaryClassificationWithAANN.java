package de.thatsich.bachelor.classification.intern.command.classification;

import org.opencv.core.Mat;

import de.thatsich.bachelor.classification.intern.command.classifier.core.BinaryClassifierConfiguration;

public class SVMBinaryClassificationWithAANN extends SVMBinaryClassification
{

	protected SVMBinaryClassificationWithAANN( BinaryClassifierConfiguration config )
	{
		super( null, config );
	}

	@Override
	public double predict( Mat image )
	{
		return 0;
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
