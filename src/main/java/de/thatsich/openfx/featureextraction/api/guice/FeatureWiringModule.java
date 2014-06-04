package de.thatsich.openfx.featureextraction.api.guice;

import com.google.inject.Scopes;
import de.thatsich.core.guice.AWiringModule;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.api.view.IFeatureDisplayView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureInputView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureListView;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureStorageService;
import de.thatsich.openfx.featureextraction.intern.model.FeatureExtractors;
import de.thatsich.openfx.featureextraction.intern.model.FeatureState;
import de.thatsich.openfx.featureextraction.intern.model.Features;
import de.thatsich.openfx.featureextraction.intern.view.FeatureDisplayView;
import de.thatsich.openfx.featureextraction.intern.view.FeatureInputView;
import de.thatsich.openfx.featureextraction.intern.view.FeatureListView;


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
		super.bind(FeatureStorageService.class).in(Scopes.SINGLETON);
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
		super.bind(IFeatures.class).to(Features.class).in(Scopes.SINGLETON);
	}
}
