package de.thatsich.bachelor.errorgeneration.restricted.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.restricted.service.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastErrorCountCommand extends ACommand<Integer> {

	// Injections
	@Inject private ErrorConfigService config;

	@Override
	protected Integer call() throws Exception {
		return this.config.getLastErrorCountInt();
	}
}
