package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.Command;

public class DeleteImageEntryCommand extends Command<ImageEntry> {

	final private ObjectProperty<ImageEntry> entry = new SimpleObjectProperty<ImageEntry>();
	
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

	// Setter 
	public void setImageEntry(ImageEntry entry) { this.entry.set(entry); }
}
