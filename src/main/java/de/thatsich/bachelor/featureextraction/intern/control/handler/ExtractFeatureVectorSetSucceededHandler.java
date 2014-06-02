package de.thatsich.bachelor.featureextraction.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for extracting the featurevector
 *
 * @author Minh
 */
public class ExtractFeatureVectorSetSucceededHandler extends ACommandHandler<FeatureVectorSet>
{
	@Inject	private IFeatureVectorSets featureVectors;

	@Override
	public void handle(FeatureVectorSet set)
	{
		this.featureVectors.list().addAll(set);
		this.log.info("Added FeatureVector to Database.");

		this.featureVectors.selectedSet().set(set);
		this.log.info("Set current to selected FeatureVectorSet.");
	}
}
