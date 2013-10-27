package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtesting.restricted.services.BinaryPredictionConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastBinaryPredictionIndexCommand extends ACommand<Void> {

	// Properties
	private final int lastBinaryPredictionIndex;
	
	// Injects
	@Inject BinaryPredictionConfigService config;
	
	@Inject
	protected SetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex) {
		this.lastBinaryPredictionIndex = lastBinaryPredictionIndex;
	}

	@Override
	protected Void call() throws Exception {
		config.setLastBinaryPredictionIndexInt(lastBinaryPredictionIndex);
		
		return null;
	}
}

