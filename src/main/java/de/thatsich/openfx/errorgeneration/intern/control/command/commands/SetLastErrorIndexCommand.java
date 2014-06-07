package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;

public class SetLastErrorIndexCommand extends ACommand<Void>
{

	// Properties
	private final int lastErrorEntryIndex;

	// Injects
	@Inject ErrorConfigService config;

	@Inject
	protected SetLastErrorIndexCommand(@Assisted int lastErrorEntryIndex)
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