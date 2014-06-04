package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import de.thatsich.core.javafx.ACommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

public class CopyFileCommand extends ACommand<Path>
{

	// Properties
	private final Path originPath;
	private final Path copyPath;

	@AssistedInject
	public CopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath)
	{
		this.originPath = originPath;
		this.copyPath = copyPath;
	}

	@Override
	protected Path call() throws IOException
	{
		if (this.originPath == null || this.copyPath == null)
		{
			throw new CancellationException("Command not initialized properly.");
		}

		if (Files.exists(this.copyPath))
		{
			this.log.info("Duplicate found: File already exists.");
			this.cancel();
			return null;
		}
		else
		{
			this.log.info("Copying File.");
			return Files.copy(this.originPath, this.copyPath);
		}
	}
}
