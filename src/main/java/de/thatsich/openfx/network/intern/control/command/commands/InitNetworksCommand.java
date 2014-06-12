package de.thatsich.openfx.network.intern.control.command.commands;

import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.command.service.NetworkFileStorageService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class InitNetworksCommand extends ACommand<List<ITrainedNetwork>>
{
	private final NetworkFileStorageService storage;

	@Inject
	InitNetworksCommand(NetworkFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<ITrainedNetwork> call() throws Exception
	{
		return this.storage.init();
	}
}
