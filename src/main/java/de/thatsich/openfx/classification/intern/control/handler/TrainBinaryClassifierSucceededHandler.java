package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for training BinaryClassifier
 *
 * @author Minh
 */
public class TrainBinaryClassifierSucceededHandler extends ACommandHandler<IBinaryClassification>
{
	@Inject private IBinaryClassifications binaryClassifications;

	@Override
	public void handle(IBinaryClassification classification)
	{
		this.binaryClassifications.binaryClassifications().add(classification);
		this.log.info("Added BinaryClassification to Database.");

		this.binaryClassifications.selectedBinaryClassification().set(classification);
		this.log.info("Select BinaryClassifcation.");
	}
}
