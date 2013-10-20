package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastBinaryClassifierIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastBinaryClassifierIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ConfigService config;
	
	@Inject
	protected SetLastBinaryClassifierIndexCommand(@Assisted int lastBinaryClassifierIndex) {
		this.lastBinaryClassifierIndex.set(lastBinaryClassifierIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastBinaryClassifierIndexInt(lastBinaryClassifierIndex.get());
				
				return null;
			}
		};
	}
}