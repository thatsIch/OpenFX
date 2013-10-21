package de.thatsich.bachelor.gridoverview.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.classificationtesting.api.core.TestWiringModule;
import de.thatsich.bachelor.classificationtraining.api.core.TrainWiringModule;
import de.thatsich.bachelor.errorgeneration.api.core.ErrorWiringModule;
import de.thatsich.bachelor.featureextraction.api.core.FeatureWiringModule;
import de.thatsich.bachelor.gridoverview.restricted.views.DisplayView;
import de.thatsich.bachelor.imageprocessing.api.core.ImageWiringModule;


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
		
		this.install(new TestWiringModule());
		this.install(new TrainWiringModule());
		this.install(new ErrorWiringModule());
		this.install(new FeatureWiringModule());
		this.install(new ImageWiringModule());
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
