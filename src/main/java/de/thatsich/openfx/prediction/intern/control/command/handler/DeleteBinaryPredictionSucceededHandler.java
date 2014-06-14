package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for testing the binary classification
 *
 * @author Minh
 */
public class DeleteBinaryPredictionSucceededHandler extends ACommandHandler<IBinaryPrediction>
{

	@Inject
	private IBinaryPredictions binaryPredictions;

	@Override
	public void handle(IBinaryPrediction value)
	{
		final List<IBinaryPrediction> binaryPredictionList = this.binaryPredictions.list();
		binaryPredictionList.remove(value);
		this.log.info("Removed BinaryPrediction from List.");

		if (binaryPredictionList.size() > 0)
		{
			final IBinaryPrediction first = binaryPredictionList.get(0);
			this.binaryPredictions.selected().set(first);
			this.log.info("Reset to first BinaryPrediction.");
		}
	}
}
