package de.thatsich.bachelor.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.errorgeneration.intern.control.command.service.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastErrorGeneratorIndexCommand extends ACommand<Void>
{
	// Properties
	private final int lastErrorGeneratorIndex;

	// Injects
	@Inject ErrorConfigService config;

	@Inject
	protected SetLastErrorGeneratorIndexCommand(@Assisted int lastErrorGeneratorIndex)
	{
		this.lastErrorGeneratorIndex = lastErrorGeneratorIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastErrorGeneratorIndexInt(this.lastErrorGeneratorIndex);

		return null;
	}
}