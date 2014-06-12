package de.thatsich.openfx.network.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.command.service.NetworkFileStorageService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class DeleteNetworkCommand extends ACommand<ITrainedNetwork>
{
	private final ITrainedNetwork toBeDeleted;
	private final NetworkFileStorageService storage;

	@Inject
	private DeleteNetworkCommand(@Assisted ITrainedNetwork toBeDeleted, NetworkFileStorageService storage)
	{
		this.toBeDeleted = toBeDeleted;
		this.storage = storage;
	}

	@Override
	protected ITrainedNetwork call() throws Exception
	{
		this.storage.delete(this.toBeDeleted);

		return this.toBeDeleted;
	}
}
