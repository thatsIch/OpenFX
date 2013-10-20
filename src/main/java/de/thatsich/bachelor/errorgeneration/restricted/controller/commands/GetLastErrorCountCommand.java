package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.core.javafx.Command;

public class GetLastErrorCountCommand extends Command<Integer> {

	// Injections
	@Inject private ErrorConfigService config;

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastErrorCountInt();
			}
		};
	}
}
