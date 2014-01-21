package de.thatsich.bachelor.gridoverview.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.classification.api.guice.ClassificationWiringModule;
import de.thatsich.bachelor.errorgeneration.api.guice.ErrorWiringModule;
import de.thatsich.bachelor.featureextraction.api.guice.FeatureWiringModule;
import de.thatsich.bachelor.gridoverview.api.core.IDisplayView;
import de.thatsich.bachelor.gridoverview.restricted.view.DisplayView;
import de.thatsich.bachelor.imageprocessing.api.guice.ImageWiringModule;
import de.thatsich.bachelor.prediction.api.guice.PredictionWiringModule;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class GridWiringModule extends AWiringModule {
	@Override
	protected void bindModule() {
		super.bind(GridWiringModule.class).toInstance(this);
		
		this.install(new PredictionWiringModule());
		this.install(new ClassificationWiringModule());
		this.install(new ErrorWiringModule());
		this.install(new FeatureWiringModule());
		this.install(new ImageWiringModule());
	}

	@Override
	protected void bindView() {
		super.bind(IDisplayView.class).to(DisplayView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController() {
	}

	@Override
	protected void bindCommand() {
	}

	@Override
	protected void bindModel() {
	}

	@Override
	protected void bindService() {
	}
}
