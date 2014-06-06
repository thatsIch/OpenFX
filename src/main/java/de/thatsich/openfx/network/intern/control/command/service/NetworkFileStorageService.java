package de.thatsich.openfx.network.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.network.api.control.Network;
import de.thatsich.openfx.network.api.model.INetworkState;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkFileStorageService extends AFileStorageService<Network>
{
	@Inject
	protected NetworkFileStorageService(INetworkState state)
	{
		super(state.path().get());
	}

	@Override
	public Network create(final Network elem)
	{
		return elem;
		// TODO create network
	}

	@Override
	public Network retrieve(final Path path)
	{
		// TODO retrieve network
		return null;
	}

	@Override
	public Network update(final Network elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(final Network elem) throws IOException
	{

	}
}
