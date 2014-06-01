package de.thatsich.bachelor.cnbc.api.guice;

import de.thatsich.core.guice.AWiringModule;

/**
 * @author thatsIch
 * @since 31.05.2014.
 */
public class NetworkWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(NetworkWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{

	}

	@Override
	protected void bindView()
	{

	}

	@Override
	protected void bindController()
	{

	}

	@Override
	protected void bindCommand()
	{

	}

	@Override
	protected void bindModel()
	{

	}
}
