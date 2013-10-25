package de.thatsich.bachelor.classificationtesting.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.PredictionState;
import de.thatsich.bachelor.classificationtesting.restricted.services.BinaryPredictionFileStorageService;
import de.thatsich.bachelor.classificationtesting.restricted.services.TestConfigService;
import de.thatsich.bachelor.classificationtesting.restricted.views.TestDisplayView;
import de.thatsich.bachelor.classificationtesting.restricted.views.TestInputView;
import de.thatsich.bachelor.classificationtesting.restricted.views.TestListView;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class TestWiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(TestWiringModule.class).toInstance(this);

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
		super.bind(TestDisplayView.class).in(Scopes.SINGLETON);
		super.bind(TestInputView.class).in(Scopes.SINGLETON);
		super.bind(TestListView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(BinaryPredictionFileStorageService.class).in(Scopes.SINGLETON);
		super.bind(TestConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(BinaryPredictions.class).in(Scopes.SINGLETON);
		super.bind(PredictionState.class).in(Scopes.SINGLETON);
	}
}
