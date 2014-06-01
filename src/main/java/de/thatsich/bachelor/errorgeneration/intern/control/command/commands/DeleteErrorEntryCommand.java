package de.thatsich.bachelor.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.errorgeneration.intern.control.error.ErrorEntry;
import de.thatsich.core.javafx.ACommand;

import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteErrorEntryCommand extends ACommand<ErrorEntry>
{

	final private ErrorEntry entry;

	@Inject
	public DeleteErrorEntryCommand(@Assisted ErrorEntry entry)
	{
		this.entry = entry;
	}

	@Override
	protected ErrorEntry call() throws Exception
	{
		Path path = this.entry.getStoragePath();
		if (Files.exists(path))
		{
			Files.delete(path);
			log.info("File deleted.");
		}

		return this.entry;
	}
}
