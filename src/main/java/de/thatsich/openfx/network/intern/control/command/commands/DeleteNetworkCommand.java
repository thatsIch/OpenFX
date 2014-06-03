package de.thatsich.openfx.network.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.network.api.control.Network;
import de.thatsich.core.javafx.ACommand;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class DeleteNetworkCommand extends ACommand<Network>
{
	final private Network toBeDeleted;

	@Inject
	private DeleteNetworkCommand(@Assisted Network toBeDeleted)
	{
		this.toBeDeleted = toBeDeleted;
	}

	@Override
	protected Network call() throws Exception
	{
		final Path toBeDeletedFilePath = this.toBeDeleted.getFilePathProperty().get();
		if (Files.exists(toBeDeletedFilePath))
		{
			Files.delete(toBeDeletedFilePath);
			this.log.info(toBeDeletedFilePath + " deleted.");
		}
		else
		{
			this.log.warning("Could not delete " + toBeDeletedFilePath + " because it was not found.");
		}

		return this.toBeDeleted;
	}
}
