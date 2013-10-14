package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.Command;

public class DeleteImageEntryCommand extends Command<ImageEntry> {

	final private ObjectProperty<ImageEntry> entry = new SimpleObjectProperty<ImageEntry>();
	
	@Inject
	public DeleteImageEntryCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted ImageEntry entry) {
		super(handler);
		this.entry.set(entry);
	}
	
	@Override
	protected Task<ImageEntry> createTask() {
		return new Task<ImageEntry>() {

			@Override
			protected ImageEntry call() throws Exception {
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
