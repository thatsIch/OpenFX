package de.thatsich.bachelor.imageprocessing.restricted.controller.commands;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.restricted.services.ImageConfigService;
import de.thatsich.core.javafx.Command;

public class GetLastImageEntryIndexCommand extends Command<Integer> {

	// Injects
	@Inject private ImageConfigService config;

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastImageIndexInt();
			}
		};
	}
}
