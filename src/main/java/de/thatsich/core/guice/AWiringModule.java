package de.thatsich.core.guice;

import com.google.inject.AbstractModule;

public abstract class AWiringModule extends AbstractModule {
	@Override
	protected final void configure() {
		this.bindModule();

		this.bindService();
		this.bindView();
		this.bindController();
		this.bindCommand();
		this.bindModel();
		
	}
	
	protected abstract void bindModule();
	protected abstract void bindView();
	protected abstract void bindController();
	protected abstract void bindCommand();
	protected abstract void bindModel();
	protected abstract void bindService();
}
