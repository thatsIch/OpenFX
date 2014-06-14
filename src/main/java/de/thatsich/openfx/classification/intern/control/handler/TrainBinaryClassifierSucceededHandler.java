package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;

/**
 * Handler for what should happen if the Command was successfull
 * for training BinaryClassifier
 *
 * @author Minh
 */
public class TrainBinaryClassifierSucceededHandler extends ACommandHandler<ITrainedBinaryClassifier>
{
	@Inject private ITrainedClassifiers binaryClassifications;

	@Override
	public void handle(ITrainedBinaryClassifier classification)
	{
		this.binaryClassifications.list().add(classification);
		this.log.info("Added BinaryClassification to Database.");

		this.binaryClassifications.selected().set(classification);
		this.log.info("Select BinaryClassifcation.");
	}
}
