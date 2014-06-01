package de.thatsich.bachelor.network.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class InitNetworkFolderCommand extends ACommand<Path>
{
	private final Path networkFolder;

	@Inject
	public InitNetworkFolderCommand(@Assisted Path networkFolder)
	{
		this.networkFolder = networkFolder;
	}

	@Override
	protected Path call() throws Exception
	{
		if (Files.notExists(networkFolder) || !Files.isDirectory(networkFolder))
		{
			Files.createDirectories(networkFolder);
		}

		return networkFolder;
	}
}
