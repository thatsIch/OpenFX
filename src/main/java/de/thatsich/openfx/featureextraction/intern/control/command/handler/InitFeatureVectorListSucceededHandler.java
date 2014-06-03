package de.thatsich.openfx.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature vector list
 *
 * @author Minh
 */
public class InitFeatureVectorListSucceededHandler extends ACommandHandler<List<FeatureVectorSet>>
{
	@Inject private IFeatureVectorSets featureVectors;

	@Override
	public void handle(List<FeatureVectorSet> featureVectorList)
	{
		featureVectors.list().addAll(featureVectorList);
		this.log.info("Added FeatureExtractor to Database.");
	}
}
