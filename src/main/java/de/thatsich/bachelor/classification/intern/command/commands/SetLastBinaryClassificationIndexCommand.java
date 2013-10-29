package de.thatsich.bachelor.classification.intern.command.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.intern.service.TrainConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastBinaryClassificationIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastBinaryClassificationIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject TrainConfigService config;
	
	@Inject
	protected SetLastBinaryClassificationIndexCommand(@Assisted int lastBinaryClassificationIndex) {
		this.lastBinaryClassificationIndex.set(lastBinaryClassificationIndex);
	}

	@Override
	protected Void call() throws Exception {
		config.setLastBinaryClassificationIndexInt(lastBinaryClassificationIndex.get());
		
		return null;
	}
}
