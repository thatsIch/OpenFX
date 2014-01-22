package de.thatsich.bachelor.preprocessing.intern.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.service.ClassificationConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastPreProcessingIndexCommand extends ACommand<Integer> {
	
	// Injections
	@Inject private ClassificationConfigService config;

	@Override
	protected Integer call() throws Exception {
		return this.config.getLastBinaryClassificationIndexInt();
	}
}
