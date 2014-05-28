package de.thatsich.bachelor.featureextraction.api.guice;

import com.google.inject.Scopes;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureDisplayView;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureInputView;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureListView;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.intern.models.FeatureExtractors;
import de.thatsich.bachelor.featureextraction.intern.models.FeatureState;
import de.thatsich.bachelor.featureextraction.intern.models.FeatureVectorSets;
import de.thatsich.bachelor.featureextraction.intern.services.CSVService;
import de.thatsich.bachelor.featureextraction.intern.services.FeatureConfigService;
import de.thatsich.bachelor.featureextraction.intern.views.FeatureDisplayView;
import de.thatsich.bachelor.featureextraction.intern.views.FeatureInputView;
import de.thatsich.bachelor.featureextraction.intern.views.FeatureListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 *
 * @author Minh
 */
public class FeatureWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(FeatureWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(FeatureConfigService.class).in(Scopes.SINGLETON);
		super.bind(CSVService.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IFeatureDisplayView.class).to(FeatureDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IFeatureInputView.class).to(FeatureInputView.class).in(Scopes.SINGLETON);
		super.bind(IFeatureListView.class).to(FeatureListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController()
	{
	}

	@Override
	protected void bindCommand()
	{

	}

	@Override
	protected void bindModel()
	{
		super.bind(IFeatureExtractors.class).to(FeatureExtractors.class).in(Scopes.SINGLETON);
		super.bind(IFeatureState.class).to(FeatureState.class).in(Scopes.SINGLETON);
		super.bind(IFeatureVectorSets.class).to(FeatureVectorSets.class).in(Scopes.SINGLETON);
	}
}
