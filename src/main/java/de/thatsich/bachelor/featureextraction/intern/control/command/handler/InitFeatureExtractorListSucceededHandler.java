package de.thatsich.bachelor.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature extractor list
 *
 * @author Minh
 */
public class InitFeatureExtractorListSucceededHandler extends ACommandHandler<List<IFeatureExtractor>>
{
	@Inject	private IFeatureExtractors featureExtractors;

	@Override
	public void handle(List<IFeatureExtractor> list)
	{
		this.featureExtractors.list().addAll(list);
		this.log.info("Added FeatureExtractor to Database.");
	}
}
