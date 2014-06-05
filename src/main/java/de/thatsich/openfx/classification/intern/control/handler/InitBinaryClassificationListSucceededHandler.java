package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature vector get
 *
 * @author Minh
 */
public class InitBinaryClassificationListSucceededHandler extends ACommandHandler<List<IBinaryClassification>>
{

	@Inject
	private IBinaryClassifications binaryClassifications;

	@Override
	public void handle(List<IBinaryClassification> trainedBinaryClassifierList)
	{
		this.binaryClassifications.list().addAll(trainedBinaryClassifierList);
		this.log.info("Added TrainedBinaryClassifierList to Database.");
	}
}
