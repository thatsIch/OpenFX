package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.restricted.services.ErrorConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastErrorEntryIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastErrorEntryIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ErrorConfigService config;
	
	@Inject
	protected SetLastErrorEntryIndexCommand(@Assisted int lastErrorEntryIndex) {
		this.lastErrorEntryIndex.set(lastErrorEntryIndex);
	}

	@Override
	protected Void call() throws Exception {
		config.setLastErrorEntryIndexInt(lastErrorEntryIndex.get());
		
		return null;
	}
}