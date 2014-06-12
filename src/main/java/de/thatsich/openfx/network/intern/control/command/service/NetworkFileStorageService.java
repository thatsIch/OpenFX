package de.thatsich.openfx.network.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworkState;

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
public class NetworkFileStorageService extends AFileStorageService<ITrainedNetwork>
{
	private static final String NETWORK_EXT = ".network";

	@Inject
	protected NetworkFileStorageService(INetworkState state)
	{
		super(state.path().get());
	}

	@Override
	public List<ITrainedNetwork> init() throws IOException
	{
		final List<ITrainedNetwork> networks = new LinkedList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath))
		{
			for (Path child : stream)
			{
				final ITrainedNetwork network = this.retrieve(child);
				networks.add(network);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All Networks extracted.");

		return networks;
	}

	@Override
	public ITrainedNetwork create(final ITrainedNetwork elem)
	{
		return elem;
		// TODO create network
	}

	@Override
	public ITrainedNetwork retrieve(final Path path)
	{
		// TODO retrieve network
		return null;
	}

	@Override
	public ITrainedNetwork update(final ITrainedNetwork elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(final ITrainedNetwork elem) throws IOException
	{
		final String fileName = elem.getConfig().toString();
		final Path filePath = this.storagePath.resolve(fileName);

		if (Files.exists(filePath))
		{
			Files.delete(filePath);
			this.log.info(filePath + " deleted.");
		}
		else
		{
			this.log.warning("Could not delete " + filePath + " because it was not found.");
		}
	}
}
