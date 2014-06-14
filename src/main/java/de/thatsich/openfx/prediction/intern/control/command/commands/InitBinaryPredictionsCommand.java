package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.BinaryPredictionFileStorageService;

import java.util.List;

public class InitBinaryPredictionsCommand extends ACommand<List<IBinaryPrediction>>
{
	private final BinaryPredictionFileStorageService storage;

	@Inject
	protected InitBinaryPredictionsCommand(BinaryPredictionFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<IBinaryPrediction> call() throws Exception
	{
		return this.storage.init();
	}
}