package de.thatsich.openfx.errorgeneration.intern.control.entity.errorgenerator;

import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;

public abstract class AErrorGenerator implements IErrorGenerator
{
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
