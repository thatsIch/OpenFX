package de.thatsich.openfx.network.intern.control.cnbc;

import de.thatsich.core.IFileStorageService;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
public class CNBCFileStorage implements IFileStorageService<CollectiveNetworkBinaryClassifiers>
{
	@Override
	public void save(final CollectiveNetworkBinaryClassifiers elem)
	{
		// TODO
	}

	@Override
	public CollectiveNetworkBinaryClassifiers load(final Path path)
	{
		// TODO
		return null;
	}
}
