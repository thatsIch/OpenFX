package de.thatsich.bachelor.featureextraction.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.featureextraction.restricted.models.FeatureExtractors;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureState;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureVectorSets;
import de.thatsich.bachelor.featureextraction.restricted.services.CSVService;
import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureDisplayView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureInputView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class FeatureWiringModule extends AWiringModule {
	@Override
	protected void bindModule() {
		super.bind(FeatureWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindView() {
		super.bind(FeatureDisplayView.class).in(Scopes.SINGLETON);
		super.bind(FeatureInputView.class).in(Scopes.SINGLETON);
		super.bind(FeatureListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController() {
	}

	@Override
	protected void bindCommand() {
	}

	@Override
	protected void bindModel() {
		super.bind(FeatureExtractors.class).in(Scopes.SINGLETON);
		super.bind(FeatureState.class).in(Scopes.SINGLETON);
		super.bind(FeatureVectorSets.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService() {
		super.bind(FeatureConfigService.class).in(Scopes.SINGLETON);
		super.bind(CSVService.class).in(Scopes.SINGLETON);
	}
}
