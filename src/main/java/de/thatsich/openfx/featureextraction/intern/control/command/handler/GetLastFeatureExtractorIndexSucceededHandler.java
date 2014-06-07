package de.thatsich.openfx.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastFeatureExtractorIndex
 *
 * @author Minh
 */
public class GetLastFeatureExtractorIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IFeatureExtractors featureExtractors;

	@Override
	public void handle(Integer value)
	{
		if (value >= 0 && this.featureExtractors.list().size() > 0)
		{
			final IFeatureExtractor selectedFeatureExtractor = this.featureExtractors.list().get(value);
			this.featureExtractors.selected().set(selectedFeatureExtractor);
			this.log.info("Set LastSelectedFeatureExtractor in Model.");
		}
	}
}
