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

public class SetLastErrorEntryIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastErrorEntryIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ConfigService config;
	
	@Inject
	protected SetLastErrorEntryIndexCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted int lastErrorEntryIndex) {
		super(handler);
		this.lastErrorEntryIndex.set(lastErrorEntryIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastErrorEntryIndexInt(lastErrorEntryIndex.get());
				
				return null;
			}
		};
	}
}