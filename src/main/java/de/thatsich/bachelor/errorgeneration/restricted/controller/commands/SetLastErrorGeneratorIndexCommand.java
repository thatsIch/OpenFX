package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastErrorGeneratorIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastErrorGeneratorIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ErrorConfigService config;
	
	@Inject
	protected SetLastErrorGeneratorIndexCommand(@Assisted int lastErrorGeneratorIndex) {
		this.lastErrorGeneratorIndex.set(lastErrorGeneratorIndex);
	}

	@Override
	protected Void call() throws Exception {
		config.setLastErrorGeneratorIndexInt(lastErrorGeneratorIndex.get());
		
		return null;
	}
}