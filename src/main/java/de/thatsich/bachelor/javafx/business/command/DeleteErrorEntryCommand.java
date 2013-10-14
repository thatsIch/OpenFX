package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.javafx.Command;

public class DeleteErrorEntryCommand extends Command<ErrorEntry> {

	final private ObjectProperty<ErrorEntry> entry = new SimpleObjectProperty<ErrorEntry>();
	
	@Inject
	public DeleteErrorEntryCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted ErrorEntry entry) {
		super(handler);
		this.entry.set(entry);
	}
	
	@Override
	protected Task<ErrorEntry> createTask() {
		return new Task<ErrorEntry>() {

			@Override
			protected ErrorEntry call() throws Exception {
				if (entry.get() == null) throw new CancellationException("Command not initialized properly.");
				
				Path path = entry.get().getPath();
				if (Files.exists(path)) {
					Files.delete(path);
					log.info("File deleted.");
				}
				
				return entry.get();
			}
		};
	}
}
