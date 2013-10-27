package de.thatsich.bachelor.imageprocessing.restricted.command.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.imageprocessing.restricted.services.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastImageEntryIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastImageEntryIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ImageConfigService config;
	
	@Inject
	protected SetLastImageEntryIndexCommand(@Assisted int lastImageEntryIndex) {
		this.lastImageEntryIndex.set(lastImageEntryIndex);
	}

	@Override
	protected Void call() throws Exception {
		config.setLastImageIndexInt(lastImageEntryIndex.get());
		
		return null;
	}
}
