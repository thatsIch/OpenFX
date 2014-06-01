package de.thatsich.bachelor.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.classification.api.model.IBinaryClassification;
import de.thatsich.bachelor.classification.api.model.IBinaryClassifications;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature vector list
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
		this.binaryClassifications.getBinaryClassificationListProperty().addAll(trainedBinaryClassifierList);
		this.log.info("Added TrainedBinaryClassifierList to Database.");
	}
}
