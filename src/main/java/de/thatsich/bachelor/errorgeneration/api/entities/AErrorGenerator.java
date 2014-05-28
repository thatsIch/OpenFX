package de.thatsich.bachelor.errorgeneration.api.entities;

public abstract class AErrorGenerator implements IErrorGenerator
{
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
