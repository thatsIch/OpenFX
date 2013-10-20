package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastErrorGeneratorIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastErrorGeneratorIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ErrorConfigService config;
	
	@Inject
	protected SetLastErrorGeneratorIndexCommand(@Assisted int lastErrorGeneratorIndex) {
		this.lastErrorGeneratorIndex.set(lastErrorGeneratorIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastErrorGeneratorIndexInt(lastErrorGeneratorIndex.get());
				
				return null;
			}
		};
	}
}