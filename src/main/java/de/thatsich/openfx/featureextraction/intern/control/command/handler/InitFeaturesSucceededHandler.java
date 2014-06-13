package de.thatsich.openfx.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature vector get
 *
 * @author Minh
 */
public class InitFeaturesSucceededHandler extends ACommandHandler<List<IFeature>>
{
	@Inject private IFeatures features;

	@Override
	public void handle(List<IFeature> features)
	{
		this.features.list().addAll(features);
		this.log.info("Added FeatureExtractor to Database.");
	}
}
