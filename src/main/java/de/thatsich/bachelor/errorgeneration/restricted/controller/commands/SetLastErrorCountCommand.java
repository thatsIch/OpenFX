package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastErrorCountCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastErrorLoopCount = new SimpleIntegerProperty();
	
	// Injects
	@Inject ErrorConfigService config;
	
	@Inject
	protected SetLastErrorCountCommand(@Assisted int lastErrorLoopCount) {
		this.lastErrorLoopCount.set(lastErrorLoopCount);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastErrorCountInt(lastErrorLoopCount.get());
				
				return null;
			}
		};
	}
}