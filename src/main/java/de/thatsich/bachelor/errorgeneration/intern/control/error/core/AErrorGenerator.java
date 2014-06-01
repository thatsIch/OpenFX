package de.thatsich.bachelor.errorgeneration.intern.control.error.core;

import de.thatsich.bachelor.errorgeneration.api.control.IErrorGenerator;

public abstract class AErrorGenerator implements IErrorGenerator
{
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
