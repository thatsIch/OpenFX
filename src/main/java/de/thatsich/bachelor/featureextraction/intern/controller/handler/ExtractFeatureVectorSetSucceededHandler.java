package de.thatsich.bachelor.featureextraction.intern.controller.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for extracting the featurevector
 *
 * @author Minh
 */
public class ExtractFeatureVectorSetSucceededHandler extends ACommandHandler<FeatureVectorSet>
{

	@Inject
	private IFeatureVectorSets featureVectors;

	@Override
	public void handle(FeatureVectorSet set)
	{
		this.featureVectors.getFeatureVectorSetListProperty().addAll(set);
		this.log.info("Added FeatureVector to Database.");

		this.featureVectors.setSelectedFeatureVectorSet(set);
		this.log.info("Set current to selected FeatureVectorSet.");
	}
}
