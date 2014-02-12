package de.thatsich.core.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.core.Log;

public abstract class ACommand<T> extends Service<T> {
	// Injects
	@Inject protected Log log;
	@Inject private Injector injector;
	
	public void setOnSucceededCommandHandler(Class<? extends ACommandHandler<T>> handlerClass) {
		final ACommandHandler<T> handler = this.injector.getInstance(handlerClass);
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
