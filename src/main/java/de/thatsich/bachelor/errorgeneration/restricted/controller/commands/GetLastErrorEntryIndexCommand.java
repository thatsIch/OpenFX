package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastErrorEntryIndexCommand extends ACommand<Integer> {

	// Injections
	@Inject private ErrorConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastErrorEntryIndexInt();
	}
}
