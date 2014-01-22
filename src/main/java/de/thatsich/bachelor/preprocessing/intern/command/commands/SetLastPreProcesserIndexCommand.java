package de.thatsich.bachelor.preprocessing.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.intern.service.ClassificationConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastPreProcesserIndexCommand extends ACommand<Void> {

	// Properties
	private final int lastBinaryClassifierIndex;
	
	// Injects
	@Inject private ClassificationConfigService config;	
	
	@Inject
	protected SetLastPreProcesserIndexCommand(@Assisted int lastBinaryClassifierIndex) {
		this.lastBinaryClassifierIndex = lastBinaryClassifierIndex;
	}

	@Override
	protected Void call() throws Exception {
		this.config.setLastBinaryClassifierIndexInt(this.lastBinaryClassifierIndex);
		
		return null;
	}
}