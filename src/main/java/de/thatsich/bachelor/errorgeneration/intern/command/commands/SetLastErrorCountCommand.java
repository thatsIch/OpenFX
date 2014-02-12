package de.thatsich.bachelor.errorgeneration.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.intern.service.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastErrorCountCommand extends ACommand<Void> {

	// Properties
	private final int lastErrorLoopCount;
	
	// Injects
	@Inject ErrorConfigService config;
	
	@Inject
	protected SetLastErrorCountCommand(@Assisted int lastErrorLoopCount) {
		this.lastErrorLoopCount = lastErrorLoopCount;
	}

	@Override
	protected Void call() throws Exception {
		this.config.setLastErrorCountInt(this.lastErrorLoopCount);
		
		return null;
	}
}