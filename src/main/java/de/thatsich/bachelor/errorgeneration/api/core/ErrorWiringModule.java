package de.thatsich.bachelor.errorgeneration.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.errorgeneration.restricted.models.ErrorEntries;
import de.thatsich.bachelor.errorgeneration.restricted.models.ErrorGenerators;
import de.thatsich.bachelor.errorgeneration.restricted.models.ErrorState;
import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.bachelor.errorgeneration.restricted.views.ErrorDisplayView;
import de.thatsich.bachelor.errorgeneration.restricted.views.ErrorInputView;
import de.thatsich.bachelor.errorgeneration.restricted.views.ErrorListView;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class ErrorWiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(ErrorWiringModule.class).toInstance(this);

		this.mapViews();
		this.mapServices();
		this.mapModels();
	}

	/*
	 * ==================================================
	 * Private Helper
	 * ==================================================
	 * used to map interfaces to implementations
	 */
	private void mapViews() {
		super.bind(ErrorDisplayView.class).in(Scopes.SINGLETON);
		super.bind(ErrorInputView.class).in(Scopes.SINGLETON);
		super.bind(ErrorListView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(ErrorConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(ErrorEntries.class).in(Scopes.SINGLETON);
		super.bind(ErrorGenerators.class).in(Scopes.SINGLETON);
		super.bind(ErrorState.class).in(Scopes.SINGLETON);
	}
}
