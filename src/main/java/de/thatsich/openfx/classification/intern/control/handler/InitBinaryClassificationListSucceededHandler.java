package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature vector get
 *
 * @author Minh
 */
public class InitBinaryClassificationListSucceededHandler extends ACommandHandler<List<ITrainedBinaryClassifier>>
{

	@Inject
	private ITrainedClassifiers binaryClassifications;

	@Override
	public void handle(List<ITrainedBinaryClassifier> trainedBinaryClassifierList)
	{
		this.binaryClassifications.list().addAll(trainedBinaryClassifierList);
		this.log.info("Added TrainedBinaryClassifierList to Database.");
	}
}
