package de.thatsich.bachelor.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.intern.control.command.service.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastErrorEntryIndexCommand extends ACommand<Integer>
{
	// Injections
	@Inject
	private ErrorConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return config.getLastErrorEntryIndexInt();
	}
}
