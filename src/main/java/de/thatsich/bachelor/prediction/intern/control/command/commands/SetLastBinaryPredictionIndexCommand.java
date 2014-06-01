package de.thatsich.bachelor.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.prediction.intern.control.command.service.BinaryPredictionConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastBinaryPredictionIndexCommand extends ACommand<Void>
{
	private final int lastBinaryPredictionIndex;

	// Injects
	@Inject
	private BinaryPredictionConfigService config;

	@Inject
	protected SetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex)
	{
		this.lastBinaryPredictionIndex = lastBinaryPredictionIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastBinaryPredictionIndexInt(this.lastBinaryPredictionIndex);

		return null;
	}
}

