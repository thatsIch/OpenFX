package de.thatsich.core.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.core.Log;

public abstract class ACommand<T> extends Service<T> {
	// Injects
	@Inject protected Log log;
	
	public void setOnSucceededCommandHandler(ACommandHandler<T> handler) {
		super.setOnSucceeded(handler);
	}
	
	@Override
	protected final Task<T> createTask() {
		return new Task<T>() {
			@Override protected T call() throws Exception {
				return ACommand.this.call();
			}
		};
	}
	
	protected abstract T call() throws Exception;
}
