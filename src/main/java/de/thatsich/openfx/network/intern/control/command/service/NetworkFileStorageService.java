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
	public void save(final Network elem)
	{
		// TODO save network
	}

	@Override
	public Network load(final Path path)
	{
		// TODO load network
		return null;
	}

	@Override
	public void update(final Network elem) throws IOException
	{

	}

	@Override
	public void delete(final Network elem) throws IOException
	{

	}
}
