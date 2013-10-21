package de.thatsich.bachelor.featureextraction.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.featureextraction.restricted.models.FeatureExtractors;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureState;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureVectorSets;
import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureDisplayView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureInputView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureListView;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class FeatureWiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(FeatureWiringModule.class).toInstance(this);

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
		super.bind(FeatureDisplayView.class).in(Scopes.SINGLETON);
		super.bind(FeatureInputView.class).in(Scopes.SINGLETON);
		super.bind(FeatureListView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(FeatureConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(FeatureExtractors.class).in(Scopes.SINGLETON);
		super.bind(FeatureState.class).in(Scopes.SINGLETON);
		super.bind(FeatureVectorSets.class).in(Scopes.SINGLETON);
	}
}
