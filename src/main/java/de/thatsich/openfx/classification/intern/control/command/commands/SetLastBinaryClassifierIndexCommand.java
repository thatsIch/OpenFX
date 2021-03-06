package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationConfigService;

public class SetLastBinaryClassifierIndexCommand extends ACommand<Void>
{
	// Properties
	private final int lastBinaryClassifierIndex;

	// Injects
	@Inject
	private ClassificationConfigService config;

	@Inject
	protected SetLastBinaryClassifierIndexCommand(@Assisted int lastBinaryClassifierIndex)
	{
		this.lastBinaryClassifierIndex = lastBinaryClassifierIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastBinaryClassifierIndexInt(this.lastBinaryClassifierIndex);

		return null;
	}
}