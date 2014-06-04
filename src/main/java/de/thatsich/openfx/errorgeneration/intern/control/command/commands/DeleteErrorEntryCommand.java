package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;

import java.io.IOException;
import java.nio.file.DirectoryStream;
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
		final Path path = this.entry.path();
		this.deleteChildren(path);
		this.deletePath(path);
		this.log.info("Error deleted.");

		return this.entry;
	}

	private void deleteChildren(Path parent)
	{
		try (DirectoryStream<Path> children = Files.newDirectoryStream(parent))
		{
			for (Path child : children)
			{
				this.deletePath(child);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void deletePath(Path path)
	{
		try
		{
			Files.deleteIfExists(path);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
