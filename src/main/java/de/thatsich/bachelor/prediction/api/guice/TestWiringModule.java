package de.thatsich.bachelor.prediction.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.prediction.intern.model.BinaryPredictions;
import de.thatsich.bachelor.prediction.intern.model.PredictionState;
import de.thatsich.bachelor.prediction.intern.service.BinaryPredictionConfigService;
import de.thatsich.bachelor.prediction.intern.service.BinaryPredictionFileStorageService;
import de.thatsich.bachelor.prediction.intern.view.BinaryPredictionSplitChannelView;
import de.thatsich.bachelor.prediction.intern.view.TestDisplayView;
import de.thatsich.bachelor.prediction.intern.view.TestInputView;
import de.thatsich.bachelor.prediction.intern.view.TestListView;


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
		super.bind(BinaryPredictionSplitChannelView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(BinaryPredictionFileStorageService.class).in(Scopes.SINGLETON);
		super.bind(BinaryPredictionConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(BinaryPredictions.class).in(Scopes.SINGLETON);
		super.bind(PredictionState.class).in(Scopes.SINGLETON);
	}
}
