package de.thatsich.bachelor.classification.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.intern.service.ClassificationConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastBinaryClassificationIndexCommand extends ACommand<Void> {

	// Properties
	private final int lastBinaryClassificationIndex;
	
	// Injects
	@Inject private ClassificationConfigService config;
	
	@Inject
	protected SetLastBinaryClassificationIndexCommand(@Assisted int lastBinaryClassificationIndex) {
		this.lastBinaryClassificationIndex = lastBinaryClassificationIndex;
	}

	@Override
	protected Void call() throws Exception {
		this.config.setLastBinaryClassificationIndexInt(this.lastBinaryClassificationIndex);
		
		return null;
	}
}
