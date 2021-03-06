package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.INetworkPredictions;

/**
 * Handler for what should happen if the Command was successful
 * for getting the LastFeatureVectorIndex
 *
 * @author Minh
 */
public class GetLastBinaryPredictionIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private INetworkPredictions binaryPredictions;

	@Override
	public void handle(Integer value)
	{
		if (0 <= value && value < this.binaryPredictions.list().size())
		{
			final INetworkPrediction selected = this.binaryPredictions.list().get(value);
			this.binaryPredictions.selected().set(selected);
			this.log.info("Set BinaryPrediction in Model.");
		}
	}
}
