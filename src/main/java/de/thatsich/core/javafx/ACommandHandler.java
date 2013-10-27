package de.thatsich.core.javafx;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public abstract class ACommandHandler<T> implements EventHandler<WorkerStateEvent> {

	@Override
	@SuppressWarnings("unchecked")
	public final void handle(WorkerStateEvent event) {
		final T value = (T) event.getSource().getValue();		
		this.handle(value);
	}
	
	public abstract void handle(T value);
}
