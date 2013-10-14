package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import de.thatsich.core.javafx.Command;

public class CopyFileCommand extends Command<Path> {
	
	// Properties
	final private ObjectProperty<Path> originPath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<Path> copyPath = new SimpleObjectProperty<Path>();
	
	@AssistedInject
	public CopyFileCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted("origin") Path originPath, @Assisted("copy") Path copyPath) {
		super(handler);
		this.originPath.set(originPath);
		this.copyPath.set(copyPath);
	}
	
	@Override
	protected Task<Path> createTask() {
		return new Task<Path>() {

			@Override
			protected Path call() throws Exception {
				if (originPath.get() == null || copyPath.get() == null) throw new CancellationException("Command not initialized properly.");
				
				if (Files.exists(copyPath.get())) {
					log.info("Duplicate found: File already exists.");
					this.cancel();
					return null;
				}
				else {
					log.info("Copying File.");
					return Files.copy(originPath.get(), copyPath.get());
				}
			}
		};
	}
}
