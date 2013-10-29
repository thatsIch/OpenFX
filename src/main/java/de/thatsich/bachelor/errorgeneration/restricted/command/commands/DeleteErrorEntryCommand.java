package de.thatsich.bachelor.errorgeneration.restricted.command.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.javafx.ACommand;

public class DeleteErrorEntryCommand extends ACommand<ErrorEntry> {

	final private ObjectProperty<ErrorEntry> entry = new SimpleObjectProperty<ErrorEntry>();
	
	@Inject
	public DeleteErrorEntryCommand(@Assisted ErrorEntry entry) {
		this.entry.set(entry);
	}

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
}
