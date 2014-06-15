package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.NetworkPredictionFileStorageService;

import java.util.List;

public class InitBinaryPredictionsCommand extends ACommand<List<INetworkPrediction>>
{
	private final NetworkPredictionFileStorageService storage;

	@Inject
	protected InitBinaryPredictionsCommand(NetworkPredictionFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<INetworkPrediction> call() throws Exception
	{
		return this.storage.init();
	}
}