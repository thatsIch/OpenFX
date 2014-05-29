package de.thatsich.bachelor.preprocessing.intern.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessing.core.APreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;

/**
 * @author thatsIch
 * @since 29.05.2014.
 */
public class IdentityPreProcessing extends APreProcessing
{
	@Inject
	public IdentityPreProcessing(@Assisted PreProcessorConfiguration config)
	{
		super(config);
	}

	@Override
	public double[] preprocess(final double[] featureVector)
	{
		return featureVector;
	}

	@Override
	public void load(final String fileName)
	{

	}

	@Override
	public void save(final String fileName)
	{

	}
}
