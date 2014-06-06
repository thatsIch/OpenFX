package de.thatsich.openfx.taboverview.api.guice;

import com.google.inject.Scopes;
import de.thatsich.core.guice.AWiringModule;
import de.thatsich.openfx.classification.api.guice.ClassificationWiringModule;
import de.thatsich.openfx.errorgeneration.api.guice.ErrorWiringModule;
import de.thatsich.openfx.featureextraction.api.guice.FeatureWiringModule;
import de.thatsich.openfx.imageprocessing.api.guice.ImageWiringModule;
import de.thatsich.openfx.network.api.guice.NetworkWiringModule;
import de.thatsich.openfx.prediction.api.guice.PredictionWiringModule;
import de.thatsich.openfx.preprocessing.api.guice.PreProcessingWiringModule;
import de.thatsich.openfx.taboverview.api.view.ITabOverviewView;
import de.thatsich.openfx.taboverview.intern.view.TabOverviewView;


public class TabOverviewWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(TabOverviewWiringModule.class).toInstance(this);

		this.install(new NetworkWiringModule());
		this.install(new PredictionWiringModule());
		this.install(new ClassificationWiringModule());
		this.install(new ErrorWiringModule());
		this.install(new PreProcessingWiringModule());
		this.install(new FeatureWiringModule());
		this.install(new ImageWiringModule());
	}

	@Override
	protected void bindModel()
	{
	}

	@Override
	protected void bindView()
	{
		super.bind(ITabOverviewView.class).to(TabOverviewView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindControl()
	{
	}

	@Override
	protected void bindCommand()
	{
	}

	@Override
	protected void bindService()
	{
	}
}
