package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.restricted.services.TrainConfigService;
import de.thatsich.core.javafx.Command;

public class GetLastTrainedBinaryClassifierIndexCommand extends Command<Integer> {
	
	// Injections
	@Inject private TrainConfigService config;

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastTrainedBinaryClassifierIndexInt();
			}
		};
	}
}
