package de.thatsich.bachelor.prediction.intern.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successful
 * for getting the LastFeatureVectorIndex
 *
 * @author Minh
 */
public class GetLastBinaryPredictionIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IBinaryPredictions binaryPredictions;

	@Override
	public void handle(Integer value)
	{
		final BinaryPrediction selectedBinaryPrediction = this.binaryPredictions.getBinaryPredictionListProperty().get(value);
		this.binaryPredictions.setSelectedBinaryPrediction(selectedBinaryPrediction);
		this.log.info("Set BinaryPrediction in Model.");
	}
}
