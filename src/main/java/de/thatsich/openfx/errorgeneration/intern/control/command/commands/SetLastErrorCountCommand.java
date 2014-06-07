package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;

public class SetLastErrorCountCommand extends ACommand<Void>
{
	private final int lastErrorLoopCount;
	private final ErrorConfigService config;

	@Inject
	protected SetLastErrorCountCommand(@Assisted int lastErrorLoopCount, ErrorConfigService config)
	{
		this.lastErrorLoopCount = lastErrorLoopCount;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastErrorCountInt(this.lastErrorLoopCount);

		return null;
	}
}