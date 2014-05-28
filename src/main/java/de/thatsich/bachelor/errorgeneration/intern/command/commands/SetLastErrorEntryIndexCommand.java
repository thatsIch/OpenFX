package de.thatsich.bachelor.errorgeneration.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.errorgeneration.intern.service.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastErrorEntryIndexCommand extends ACommand<Void>
{

	// Properties
	private final int lastErrorEntryIndex;

	// Injects
	@Inject ErrorConfigService config;

	@Inject
	protected SetLastErrorEntryIndexCommand(@Assisted int lastErrorEntryIndex)
	{
		this.lastErrorEntryIndex = lastErrorEntryIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastErrorEntryIndexInt(this.lastErrorEntryIndex);

		return null;
	}
}