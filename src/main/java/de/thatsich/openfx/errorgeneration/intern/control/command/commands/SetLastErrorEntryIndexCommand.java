package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;
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