package de.thatsich.bachelor.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastFeatureExtractorIndex
 *
 * @author Minh
 */
public class GetLastFeatureExtractorIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IFeatureExtractors featureExtractors;

	@Override
	public void handle(Integer value)
	{
		final IFeatureExtractor selectedFeatureExtractor = this.featureExtractors.getFeatureExtractorsProperty().get(value);
		this.featureExtractors.getSelectedFeatureExtractorProperty().set(selectedFeatureExtractor);
		this.log.info("Set LastSelectedFeatureExtractor in Model.");
	}
}