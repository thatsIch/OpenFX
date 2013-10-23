package de.thatsich.bachelor.classificationtraining.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.classificationtraining.restricted.model.logic.RandomForestBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.model.logic.SVMBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.BinaryClassifications;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.BinaryClassifiers;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.TrainState;
import de.thatsich.bachelor.classificationtraining.restricted.services.TrainConfigService;
import de.thatsich.bachelor.classificationtraining.restricted.views.TrainDisplayView;
import de.thatsich.bachelor.classificationtraining.restricted.views.TrainInputView;
import de.thatsich.bachelor.classificationtraining.restricted.views.TrainListView;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class TrainWiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(TrainWiringModule.class).toInstance(this);

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
		super.bind(TrainDisplayView.class).in(Scopes.SINGLETON);
		super.bind(TrainInputView.class).in(Scopes.SINGLETON);
		super.bind(TrainListView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(TrainConfigService.class).in(Scopes.SINGLETON);

		super.bind(SVMBinaryClassifier.class).in(Scopes.SINGLETON);
		super.bind(RandomForestBinaryClassifier.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(BinaryClassifiers.class).in(Scopes.SINGLETON);
		super.bind(BinaryClassifications.class).in(Scopes.SINGLETON);
		super.bind(TrainState.class).in(Scopes.SINGLETON);
	}
}
