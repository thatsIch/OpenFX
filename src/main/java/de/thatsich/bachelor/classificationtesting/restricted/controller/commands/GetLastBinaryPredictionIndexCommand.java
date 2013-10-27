package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.restricted.services.BinaryPredictionConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastBinaryPredictionIndexCommand extends ACommand<Integer> {
	
	// Injections
	@Inject private BinaryPredictionConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastBinaryPredictionIndexInt();
	}
}