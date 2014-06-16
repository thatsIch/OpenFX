package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.NetworkPredictionFileStorageService;

public class DeleteBinaryPredictionCommand extends ACommand<INetworkPrediction>
{
	final INetworkPrediction prediction;
	final NetworkPredictionFileStorageService storage;

	@Inject
	DeleteBinaryPredictionCommand(@Assisted INetworkPrediction prediction, final NetworkPredictionFileStorageService storage)
	{
		this.prediction = prediction;
		this.storage = storage;
	}

	@Override
	protected INetworkPrediction call() throws Exception
	{
		this.storage.delete(this.prediction);

		return this.prediction;
	}
}
