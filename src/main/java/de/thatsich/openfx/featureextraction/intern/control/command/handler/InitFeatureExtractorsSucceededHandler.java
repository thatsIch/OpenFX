package de.thatsich.openfx.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature extractor get
 *
 * @author Minh
 */
public class InitFeatureExtractorsSucceededHandler extends ACommandHandler<List<IFeatureExtractor>>
{
	@Inject private IFeatureExtractors featureExtractors;

	@Override
	public void handle(List<IFeatureExtractor> list)
	{
		this.featureExtractors.list().addAll(list);
		this.log.info("Added FeatureExtractor to Database.");
	}
}
