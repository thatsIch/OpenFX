package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.BinaryPredictionFileStorageService;

public class DeleteBinaryPredictionCommand extends ACommand<IBinaryPrediction>
{
	final IBinaryPrediction prediction;
	final BinaryPredictionFileStorageService storage;

	@Inject
	private DeleteBinaryPredictionCommand(@Assisted IBinaryPrediction prediction, final BinaryPredictionFileStorageService storage)
	{
		this.prediction = prediction;
		this.storage = storage;
	}

	@Override
	protected IBinaryPrediction call() throws Exception
	{
		this.storage.delete(this.prediction);
		return this.prediction;
	}
}
