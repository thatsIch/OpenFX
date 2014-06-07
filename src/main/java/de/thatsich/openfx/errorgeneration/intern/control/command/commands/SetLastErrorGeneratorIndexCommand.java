package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;

public class SetLastErrorGeneratorIndexCommand extends ACommand<Void>
{
	private final int lastErrorGeneratorIndex;
	private final ErrorConfigService config;

	@Inject
	protected SetLastErrorGeneratorIndexCommand(@Assisted int lastErrorGeneratorIndex, ErrorConfigService config)
	{
		this.lastErrorGeneratorIndex = lastErrorGeneratorIndex;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastErrorGeneratorIndexInt(this.lastErrorGeneratorIndex);

		return null;
	}
}