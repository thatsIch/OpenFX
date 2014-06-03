package de.thatsich.openfx.errorgeneration.intern.control.error.core;

import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;

public abstract class AErrorGenerator implements IErrorGenerator
{
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
