package de.thatsich.openfx.featureextraction.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for removing the featurevector
 *
 * @author Minh
 */
public class RemoveFeatureSucceededHandler extends ACommandHandler<IFeature>
{
	@Inject private IFeatures features;

	@Override
	public void handle(IFeature feature)
	{
		final List<IFeature> list = this.features.list();
		list.remove(feature);
		this.log.info("Removed FeatureVector from Database.");

		if (list.size() > 0)
		{
			final IFeature first = list.get(0);
			this.features.selectedFeature().set(first);
			this.log.info("Reset Selection to first FeatureVectorSet.");
		}
		else
		{
			this.features.selectedFeature().set(null);
			this.log.info("Reset Selection to null.");
		}
	}
}
