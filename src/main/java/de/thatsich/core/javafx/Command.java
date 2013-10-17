package de.thatsich.core.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;

import de.thatsich.core.Log;

public abstract class Command<T> extends Service<T> {
	// Injects
	@Inject protected Log log;
}
