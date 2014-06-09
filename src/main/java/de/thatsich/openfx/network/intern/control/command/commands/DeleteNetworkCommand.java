package de.thatsich.openfx.network.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.network.api.control.entity.INetwork;
import de.thatsich.openfx.network.intern.control.command.service.NetworkFileStorageService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class DeleteNetworkCommand extends ACommand<INetwork>
{
	private final INetwork toBeDeleted;
	private final NetworkFileStorageService storage;

	@Inject
	private DeleteNetworkCommand(@Assisted INetwork toBeDeleted, NetworkFileStorageService storage)
	{
		this.toBeDeleted = toBeDeleted;
		this.storage = storage;
	}

	@Override
	protected INetwork call() throws Exception
	{
		this.storage.delete(this.toBeDeleted);

		return this.toBeDeleted;
	}
}
