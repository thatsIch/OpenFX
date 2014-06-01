package de.thatsich.bachelor.taboverview.api.guice;

import com.google.inject.Scopes;
import de.thatsich.bachelor.classification.api.guice.ClassificationWiringModule;
import de.thatsich.bachelor.network.api.guice.NetworkWiringModule;
import de.thatsich.bachelor.errorgeneration.api.guice.ErrorWiringModule;
import de.thatsich.bachelor.featureextraction.api.guice.FeatureWiringModule;
import de.thatsich.bachelor.imageprocessing.api.guice.ImageWiringModule;
import de.thatsich.bachelor.prediction.api.guice.PredictionWiringModule;
import de.thatsich.bachelor.preprocessing.api.guice.PreProcessingWiringModule;
import de.thatsich.bachelor.taboverview.api.core.ITabOverviewView;
import de.thatsich.bachelor.taboverview.intern.view.TabOverviewView;
import de.thatsich.core.guice.AWiringModule;


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
	protected void bindService()
	{
	}

	@Override
	protected void bindView()
	{
		super.bind(ITabOverviewView.class).to(TabOverviewView.class).in(Scopes.SINGLETON);
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
	}
}
