package de.thatsich.bachelor.prediction.intern.command.commands;

import com.google.inject.Inject;
import de.thatsich.bachelor.prediction.intern.service.BinaryPredictionConfigService;
import de.thatsich.core.javafx.ACommand;

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