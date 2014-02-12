package de.thatsich.bachelor.imageprocessing.intern.command.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.ACommand;

public class DeleteImageEntryCommand extends ACommand<ImageEntry> {

	final private ImageEntry entry;
	
	@Inject
	public DeleteImageEntryCommand(@Assisted ImageEntry entry) {
		this.entry = entry;
	}

	@Override
	protected ImageEntry call() throws Exception {
		final Path path = this.entry.getImagePath();
		if (Files.exists(path)) {
			Files.delete(path);
			log.info("File deleted.");
		}
		
		return this.entry;
	}
}
