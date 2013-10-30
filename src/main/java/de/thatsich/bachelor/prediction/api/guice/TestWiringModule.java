package de.thatsich.bachelor.prediction.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.prediction.intern.model.BinaryPredictions;
import de.thatsich.bachelor.prediction.intern.model.PredictionState;
import de.thatsich.bachelor.prediction.intern.service.BinaryPredictionConfigService;
import de.thatsich.bachelor.prediction.intern.service.BinaryPredictionFileStorageService;
import de.thatsich.bachelor.prediction.intern.view.BinaryPredictionSplitChannelView;
import de.thatsich.bachelor.prediction.intern.view.TestDisplayView;
import de.thatsich.bachelor.prediction.intern.view.TestInputView;
import de.thatsich.bachelor.prediction.intern.view.TestListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class TestWiringModule extends AWiringModule {
	@Override
	protected void bindModule() {
		super.bind(TestWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindView() {
		super.bind(TestDisplayView.class).in(Scopes.SINGLETON);
		super.bind(TestInputView.class).in(Scopes.SINGLETON);
		super.bind(TestListView.class).in(Scopes.SINGLETON);
		super.bind(BinaryPredictionSplitChannelView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController() {
	}

	@Override
	protected void bindCommand() {
	}

	@Override
	protected void bindModel() {
		super.bind(BinaryPredictions.class).in(Scopes.SINGLETON);
		super.bind(PredictionState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService() {
		super.bind(BinaryPredictionFileStorageService.class).in(Scopes.SINGLETON);
		super.bind(BinaryPredictionConfigService.class).in(Scopes.SINGLETON);
	}
}
