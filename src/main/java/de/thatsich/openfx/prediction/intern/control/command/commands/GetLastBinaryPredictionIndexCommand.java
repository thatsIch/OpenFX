package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.prediction.intern.control.command.service.BinaryPredictionConfigService;

public class GetLastBinaryPredictionIndexCommand extends ACommand<Integer>
{

	// Injections
	@Inject
	private BinaryPredictionConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return config.getLastBinaryPredictionIndexInt();
	}
}