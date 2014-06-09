package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.prediction.intern.control.command.service.BinaryPredictionFileStorageService;
import de.thatsich.openfx.prediction.intern.control.entity.BinaryPrediction;

import java.util.List;

public class InitBinaryPredictionsCommand extends ACommand<List<BinaryPrediction>>
{
	private final BinaryPredictionFileStorageService storage;

	@Inject
	protected InitBinaryPredictionsCommand(BinaryPredictionFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<BinaryPrediction> call() throws Exception
	{
		return this.storage.init();
	}
}