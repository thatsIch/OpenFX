package de.thatsich.bachelor.javafx.business.command;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.opencv.error.LineError;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IErrorGenerator;

public class InitErrorGeneratorListCommand extends Command<List<IErrorGenerator>> {

	@Inject
	protected InitErrorGeneratorListCommand(@Assisted EventHandler<WorkerStateEvent> handler) {
		super(handler);
	}

	@Override
	protected Task<List<IErrorGenerator>> createTask() {
		// TODO Auto-generated method stub
		return new Task<List<IErrorGenerator>>() {

			@Override
			protected List<IErrorGenerator> call() throws Exception {
				final List<IErrorGenerator> errorGeneratorList = new ArrayList<IErrorGenerator>();
				
				errorGeneratorList.add(new LineError());
				
				return errorGeneratorList;
			}
		};
	}

}