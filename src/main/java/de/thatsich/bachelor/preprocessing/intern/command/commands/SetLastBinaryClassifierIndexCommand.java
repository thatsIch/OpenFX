package de.thatsich.bachelor.classification.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.intern.service.ClassificationConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastBinaryClassifierIndexCommand extends ACommand<Void> {

	// Properties
	private final int lastBinaryClassifierIndex;
	
	// Injects
	@Inject private ClassificationConfigService config;	
	
	@Inject
	protected SetLastBinaryClassifierIndexCommand(@Assisted int lastBinaryClassifierIndex) {
		this.lastBinaryClassifierIndex = lastBinaryClassifierIndex;
	}

	@Override
	protected Void call() throws Exception {
		this.config.setLastBinaryClassifierIndexInt(this.lastBinaryClassifierIndex);
		
		return null;
	}
}