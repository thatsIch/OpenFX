package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
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
