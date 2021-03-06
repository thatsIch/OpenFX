package de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core;

import com.google.inject.Inject;
import de.thatsich.core.Log;

public abstract class APreProcessor implements IPreProcessor
{
	// Injects
	@Inject
	protected Log log;

	@Override
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
