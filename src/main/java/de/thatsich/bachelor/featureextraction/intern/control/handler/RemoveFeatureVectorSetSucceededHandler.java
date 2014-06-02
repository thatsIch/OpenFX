package de.thatsich.bachelor.featureextraction.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for removing the featurevector
 *
 * @author Minh
 */
public class RemoveFeatureVectorSetSucceededHandler extends ACommandHandler<FeatureVectorSet>
{
	@Inject	private IFeatureVectorSets featureVectors;

	@Override
	public void handle(FeatureVectorSet fv)
	{
		final List<FeatureVectorSet> list = this.featureVectors.list();
		list.remove(fv);
		this.log.info("Removed FeatureVector from Database.");

		if (list.size() > 0)
		{
			final FeatureVectorSet first = list.get(0);
			this.featureVectors.selectedSet().set(first);
			this.log.info("Reset Selection to first FeatureVectorSet.");
		}
		else
		{
			this.featureVectors.selectedSet().set(null);
			this.log.info("Reset Selection to null.");
		}
	}
}
