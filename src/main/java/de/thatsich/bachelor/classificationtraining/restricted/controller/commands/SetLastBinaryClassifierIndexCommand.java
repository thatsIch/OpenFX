package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.restricted.services.TrainConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastBinaryClassifierIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastBinaryClassifierIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject TrainConfigService config;
	
	@Inject
	protected SetLastBinaryClassifierIndexCommand(@Assisted int lastBinaryClassifierIndex) {
		this.lastBinaryClassifierIndex.set(lastBinaryClassifierIndex);
	}

	@Override
	protected Void call() throws Exception {
		config.setLastBinaryClassifierIndexInt(lastBinaryClassifierIndex.get());
		
		return null;
	}
}