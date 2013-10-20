package de.thatsich.bachelor.gridoverview.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.gridoverview.restricted.views.DisplayView;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class GridWiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(GridWiringModule.class).toInstance(this);

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
		super.bind(DisplayView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {

	}
	
	private void mapModels() {

	}
}
