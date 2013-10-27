package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.restricted.services.TrainConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastBinaryClassifierIndexCommand extends ACommand<Integer> {

	// Injections
	@Inject private TrainConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastBinaryClassifierIndexInt();
	}
}
