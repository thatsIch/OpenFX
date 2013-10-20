package de.thatsich.bachelor.imageprocessing.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.imageprocessing.restricted.services.ImageConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastImageEntryIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastImageEntryIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ImageConfigService config;
	
	@Inject
	protected SetLastImageEntryIndexCommand(@Assisted int lastImageEntryIndex) {
		this.lastImageEntryIndex.set(lastImageEntryIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastImageIndexInt(lastImageEntryIndex.get());
				
				return null;
			}
		};
	}
}
