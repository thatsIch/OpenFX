package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;

public class GetLastErrorIndexCommand extends ACommand<Integer>
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
