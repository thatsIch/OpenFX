package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import javafx.collections.ObservableList;

/**
 * Handler for what should happen if the Command was successfull
 * for deleting the error
 *
 * @author Minh
 */
public class RemoveBinaryClassificationSucceededHandler extends ACommandHandler<ITrainedBinaryClassifier>
{
	@Inject private ITrainedClassifiers binaryClassifications;

	@Override
	public void handle(ITrainedBinaryClassifier deletion)
	{
		final ObservableList<ITrainedBinaryClassifier> binaryClassificationList = this.binaryClassifications.list();
		binaryClassificationList.remove(deletion);
		log.info("Removed BinaryClassification from Database.");

		if (binaryClassificationList.size() > 0)
		{
			final ITrainedBinaryClassifier first = binaryClassificationList.get(0);
			this.binaryClassifications.selected().set(first);
			this.log.info("Reset Selection to first BinaryClassifcation.");
		}
		else
		{
			this.binaryClassifications.selected().set(null);
			this.log.info("Reset Selection to null.");
		}
	}
}
