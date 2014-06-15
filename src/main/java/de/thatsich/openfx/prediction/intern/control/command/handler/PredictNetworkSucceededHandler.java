package de.thatsich.openfx.prediction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.INetworkPredictions;

/**
 * Handler for what should happen if the Command was successfull
 * for testing the binary classification
 *
 * @author Minh
 */
public class PredictNetworkSucceededHandler extends ACommandHandler<INetworkPrediction>
{

	@Inject
	private INetworkPredictions networkPredictions;

	@Override
	public void handle(INetworkPrediction prediction)
	{
		this.networkPredictions.list().add(prediction);
		this.log.info("Added BinaryPrediction to Database.");

		this.networkPredictions.selected().set(prediction);
		this.log.info("Set current to selected BinaryPrediction.");
	}
}
