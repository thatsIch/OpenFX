package de.thatsich.bachelor.network.intern.service;

import de.thatsich.bachelor.network.entities.Network;
import de.thatsich.core.IFileStorageService;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkFileStorageService implements IFileStorageService<Network>
{
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
}
