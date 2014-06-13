package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationConfigService;

public class SetLastBinaryClassificationIndexCommand extends ACommand<Void>
{
	// Properties
	private final int lastBinaryClassificationIndex;

	// Injects
	@Inject
	private ClassificationConfigService config;

	@Inject
	protected SetLastBinaryClassificationIndexCommand(@Assisted int lastBinaryClassificationIndex)
	{
		this.lastBinaryClassificationIndex = lastBinaryClassificationIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastBinaryClassificationIndexInt(this.lastBinaryClassificationIndex);

		return null;
	}
}
