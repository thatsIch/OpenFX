package de.thatsich.core.guice;

import com.google.inject.AbstractModule;

/**
 * Guice Graph of the whole MVP structure
 *
 * @author Minh
 */
public abstract class AWiringModule extends AbstractModule
{
	@Override
	protected final void configure()
	{
		this.bindModule();

		this.bindModel();
		this.bindView();
		this.bindControl();
		this.bindCommand();
		this.bindService();
	}

	protected abstract void bindModule();

	protected abstract void bindModel();

	protected abstract void bindView();

	protected abstract void bindControl();

	protected abstract void bindCommand();

	protected abstract void bindService();
}
