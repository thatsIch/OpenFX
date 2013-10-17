package de.thatsich.bachelor.javafx.business.command;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastImageEntryIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastImageEntryIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ConfigService config;
	
	@Inject
	protected SetLastImageEntryIndexCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted int lastImageEntryIndex) {
		super(handler);
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
