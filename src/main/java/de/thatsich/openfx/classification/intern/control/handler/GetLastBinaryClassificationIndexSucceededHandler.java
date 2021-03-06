package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;

/**
 * Handler for what should happen if the Command was successfull
 * for getting the LastFeatureVectorIndex
 *
 * @author Minh
 */
public class GetLastBinaryClassificationIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private ITrainedClassifiers binaryClassifications;

	@Override
	public void handle(Integer value)
	{
		if (value >= 0 && this.binaryClassifications.list().size() > 0)
		{
			final ITrainedBinaryClassifier selected = this.binaryClassifications.list().get(value);
			this.binaryClassifications.selected().set(selected);
			this.log.info("Set last selected BinaryClassification in Model.");
		}
	}
}