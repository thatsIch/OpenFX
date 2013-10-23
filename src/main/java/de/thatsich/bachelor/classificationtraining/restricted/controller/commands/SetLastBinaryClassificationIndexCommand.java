package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.restricted.services.TrainConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastBinaryClassificationIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastBinaryClassificationIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject TrainConfigService config;
	
	@Inject
	protected SetLastBinaryClassificationIndexCommand(@Assisted int lastBinaryClassificationIndex) {
		this.lastBinaryClassificationIndex.set(lastBinaryClassificationIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastBinaryClassificationIndexInt(lastBinaryClassificationIndex.get());
				
				return null;
			}
		};
	}
}
