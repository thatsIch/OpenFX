package de.thatsich.bachelor.javafx.business.command;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.Command;

public class GetLastErrorEntryIndexCommand extends Command<Integer> {

	// Injections
	@Inject private ConfigService config;

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastErrorEntryIndexInt();
			}
		};
	}
}
