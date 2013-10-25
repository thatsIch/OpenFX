package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtesting.restricted.services.BinaryPredictionConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastBinaryPredictionIndexCommand extends Command<Void> {

	// Properties
	private final int lastBinaryPredictionIndex;
	
	// Injects
	@Inject BinaryPredictionConfigService config;
	
	@Inject
	protected SetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex) {
		this.lastBinaryPredictionIndex = lastBinaryPredictionIndex;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastBinaryPredictionIndexInt(lastBinaryPredictionIndex);
				
				return null;
			}
		};
	}
}

