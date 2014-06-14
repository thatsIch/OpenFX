package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the feature vector get
 *
 * @author Minh
 */
public class InitBinaryPredictionListSucceededHandler extends ACommandHandler<List<IBinaryPrediction>>
{

	@Inject
	private IBinaryPredictions binaryPredictions;

	@Override
	public void handle(List<IBinaryPrediction> binaryPredictionList)
	{
		this.binaryPredictions.list().addAll(binaryPredictionList);
		this.log.info("Added BinaryPredictionList to Database.");
	}
}
