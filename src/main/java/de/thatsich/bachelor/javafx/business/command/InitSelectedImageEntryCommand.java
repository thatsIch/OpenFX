package de.thatsich.bachelor.javafx.business.command;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.Command;

public class InitSelectedImageEntryCommand extends Command<Integer> {

	@Inject ConfigService config;
	
	@Inject
	protected InitSelectedImageEntryCommand(@Assisted EventHandler<WorkerStateEvent> handler) {
		super(handler);
	}

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastImageIndexInt();
			}
		};
	}

}
