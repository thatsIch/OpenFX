package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import de.thatsich.openfx.prediction.intern.control.entity.BinaryPrediction;

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
		if (value >= 0 && this.binaryPredictions.list().size() > 0)
		{
			final BinaryPrediction selected = this.binaryPredictions.list().get(value);
			this.binaryPredictions.selected().set(selected);
			this.log.info("Set BinaryPrediction in Model.");
		}
	}
}
