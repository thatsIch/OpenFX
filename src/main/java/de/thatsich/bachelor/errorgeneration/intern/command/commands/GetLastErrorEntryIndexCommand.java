package de.thatsich.bachelor.errorgeneration.intern.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.intern.service.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastErrorEntryIndexCommand extends ACommand<Integer> {

	// Injections
	@Inject private ErrorConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastErrorEntryIndexInt();
	}
}
