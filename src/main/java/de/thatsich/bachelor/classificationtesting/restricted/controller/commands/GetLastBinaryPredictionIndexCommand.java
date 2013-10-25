package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.restricted.services.BinaryPredictionConfigService;
import de.thatsich.core.javafx.Command;

public class GetLastBinaryPredictionIndexCommand extends Command<Integer> {
	
	// Injections
	@Inject private BinaryPredictionConfigService config;

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastBinaryPredictionIndexInt();
			}
		};
	}
}