package de.thatsich.bachelor.network.intern.command.commands;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.network.entities.Network;
import de.thatsich.bachelor.network.intern.service.NetworkFileStorageService;
import de.thatsich.core.javafx.ACommand;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class InitNetworkListCommand extends ACommand<List<Network>>
{
	private final Path networkPath;

	@Inject NetworkFileStorageService fileStorage;

	@Inject
	InitNetworkListCommand(@Assisted Path networkPath)
	{
		this.networkPath = networkPath;
	}

	@Override
	protected List<Network> call() throws Exception
	{
		final List<Network> networkList = new LinkedList<>();
		final String GLOB_PATTERN = "*.{network}";

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(networkPath, GLOB_PATTERN))
		{
			for (Path child : stream)
			{
				final Network network = fileStorage.load(child);
				networkList.add(network);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		log.info("All Networks extracted.");

		return networkList;
	}
}
