package de.thatsich.core.guice;

import com.google.inject.AbstractModule;

public abstract class AWiringModule extends AbstractModule {
	@Override
	protected final void configure() {
		this.bindModule();

		this.bindView();
		this.bindViewModel();
		this.bindCommand();
		this.bindModel();
		this.bindService();
	}
	
	protected abstract void bindModule();
	protected abstract void bindView();
	protected abstract void bindViewModel();
	protected abstract void bindCommand();
	protected abstract void bindModel();
	protected abstract void bindService();
}
