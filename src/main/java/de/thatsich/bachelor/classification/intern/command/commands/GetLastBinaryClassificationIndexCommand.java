package de.thatsich.bachelor.classification.intern.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.service.TrainConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastBinaryClassificationIndexCommand extends ACommand<Integer> {
	
	// Injections
	@Inject private TrainConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastBinaryClassificationIndexInt();
	}
}
