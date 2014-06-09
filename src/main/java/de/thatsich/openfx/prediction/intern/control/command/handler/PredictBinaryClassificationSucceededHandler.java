package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import de.thatsich.openfx.prediction.intern.control.entity.BinaryPrediction;

/**
 * Handler for what should happen if the Command was successfull
 * for testing the binary classification
 *
 * @author Minh
 */
public class PredictBinaryClassificationSucceededHandler extends ACommandHandler<BinaryPrediction>
{

	@Inject
	private IBinaryPredictions binaryPredictions;

	@Override
	public void handle(BinaryPrediction prediction)
	{
		this.binaryPredictions.list().add(prediction);
		this.log.info("Added BinaryPrediction to Database.");

		this.binaryPredictions.selected().set(prediction);
		this.log.info("Set current to selected BinaryPrediction.");
	}
}
