package de.thatsich.core.javafx;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;

import de.thatsich.core.Log;

public abstract class ACommandHandler<T> implements EventHandler<WorkerStateEvent> {

	@Inject protected Log log;
	
	@Override
	@SuppressWarnings("unchecked")
	public final void handle(WorkerStateEvent event) {
		final T value = (T) event.getSource().getValue();		
		if (value != null) {
			this.handle(value);
		}
	}
	
	public abstract void handle(T value);
}
