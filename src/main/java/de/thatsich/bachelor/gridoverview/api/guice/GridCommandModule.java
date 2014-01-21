package de.thatsich.bachelor.gridoverview.api.guice;

import java.util.List;

import de.thatsich.bachelor.classification.api.guice.ClassificationCommandModule;
import de.thatsich.bachelor.errorgeneration.api.guice.ErrorCommandModule;
import de.thatsich.bachelor.featureextraction.api.guice.FeatureCommandModule;
import de.thatsich.bachelor.imageprocessing.api.guice.ImageCommandModule;
import de.thatsich.bachelor.prediction.api.guice.PredictionCommandModule;
import de.thatsich.core.guice.ACommandModule;
import de.thatsich.core.guice.ICommandProvider;


/**
 * Guice Module
 * 
 * @see ACommandModule
 * 
 * @author thatsIch
 */
public class GridCommandModule extends ACommandModule
{
	@Override
	protected void buildProviderModule( List<Class<? extends ICommandProvider>> providerList )
	{
		this.install( new PredictionCommandModule() );
		this.install( new ClassificationCommandModule() );
		this.install( new ErrorCommandModule() );
		this.install( new FeatureCommandModule() );
		this.install( new ImageCommandModule() );
	}
}
