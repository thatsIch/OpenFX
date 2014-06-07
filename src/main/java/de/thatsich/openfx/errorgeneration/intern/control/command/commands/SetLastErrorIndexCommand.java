package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;

public class SetLastErrorIndexCommand extends ACommand<Void>
{
	private final int lastErrorEntryIndex;
	private final ErrorConfigService config;

	@Inject
	protected SetLastErrorIndexCommand(@Assisted int lastErrorEntryIndex, ErrorConfigService config)
	{
		this.lastErrorEntryIndex = lastErrorEntryIndex;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastErrorEntryIndexInt(this.lastErrorEntryIndex);

		return null;
	}
}