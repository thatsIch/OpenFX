package de.thatsich.openfx.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;

/**
 * @author thatsIch
 * @since 06.06.2014.
 */
public class GetLastFeatureIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IFeatures features;

	@Override
	public void handle(final Integer value)
	{
		if (value >= 0 && this.features.list().size() > 0)
		{
			final IFeature selected = this.features.list().get(value);
			this.features.selected().set(selected);
			this.log.info("Set LastFeatureIndex in Model.");
		}
	}
}
