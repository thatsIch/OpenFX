package de.thatsich.openfx.featureextraction.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;

/**
 * Handler for what should happen if the Command was successfull
 * for extracting the featurevector
 *
 * @author Minh
 */
public class ExtractFeatureSucceededHandler extends ACommandHandler<IFeature>
{
	@Inject private IFeatures features;

	@Override
	public void handle(IFeature feature)
	{
		this.features.list().add(feature);
		this.log.info("Added FeatureVector to Database.");

		this.features.selectedFeature().set(feature);
		this.log.info("Set current to selected FeatureVectorSet.");
	}
}
