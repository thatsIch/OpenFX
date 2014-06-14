package de.thatsich.openfx.network.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworkState;
import de.thatsich.openfx.network.intern.control.prediction.NetworkConfig;
import de.thatsich.openfx.network.intern.control.prediction.TrainedNetwork;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.CollectiveNetworkBinaryClassifiers;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.NetworkBinaryClassifiers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkFileStorageService extends AFileStorageService<ITrainedNetwork>
{
	private final IFeatureExtractors featureExtractors;
	private final ITrainedClassifiers classifiers;

	@Inject
	protected NetworkFileStorageService(INetworkState state, IFeatureExtractors featureExtractors, ITrainedClassifiers classifiers)
	{
		super(state.path().get());
		this.featureExtractors = featureExtractors;
		this.classifiers = classifiers;
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
	public ITrainedNetwork create(final ITrainedNetwork network) throws IOException
	{
		final NetworkConfig config = network.getConfig();
		final String fileName = config.toString();
		final Path filePath = super.storagePath.resolve(fileName);
		this.createInvalidDirectory(filePath);

		final ICNBC cnbc = network.getCnbc();
		final List<INBC> nbcs = cnbc.getNbcs();

		for (final INBC nbc : nbcs)
		{
			final Path nbcPath = filePath.resolve(nbc.getUniqueErrorClassName());

			try (final BufferedWriter writer = Files.newBufferedWriter(nbcPath, StandardCharsets.US_ASCII))
			{
				final StringJoiner joiner = new StringJoiner(System.lineSeparator());
				final List<ITrainedBinaryClassifier> trainedBinaryClassifiers = nbc.getTrainedBinaryClassifier();
				for (ITrainedBinaryClassifier bc : trainedBinaryClassifiers)
				{
					joiner.add(bc.getConfig().toString());
				}

				writer.write(joiner.toString());
			}
		}
		this.log.info("Stored Network " + network);

		return network;
	}

	@Override
	public ITrainedNetwork retrieve(final Path path)
	{
		final String fileName = path.getFileName().toString();
		final NetworkConfig config = new NetworkConfig(fileName);

		final List<INBC> nbcs = new LinkedList<>();
		final Set<String> uniqueErrorClassNames = new HashSet<>();
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path))
		{
			for (Path dir : dirStream)
			{
				final INBC inbc = this.retrieveNBC(dir);
				nbcs.add(inbc);
				uniqueErrorClassNames.add(inbc.getUniqueErrorClassName());
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}

		final ICNBC cnbc = new CollectiveNetworkBinaryClassifiers(nbcs, uniqueErrorClassNames, this.log);
		final ITrainedNetwork trained = new TrainedNetwork(config, this.featureExtractors.list(), cnbc);

		return trained;
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

	private INBC retrieveNBC(Path path) throws IOException
	{
		final String errorClassName = path.getFileName().toString();

		final List<ITrainedBinaryClassifier> bcs = new LinkedList<>();
		try (final BufferedReader vectorReader = Files.newBufferedReader(path, StandardCharsets.US_ASCII))
		{
			String trainedClassifierID;
			while ((trainedClassifierID = vectorReader.readLine()) != null)
			{
				for (ITrainedBinaryClassifier bc : this.classifiers.list())
				{
					final String bcID = bc.getConfig().toString();
					if (bcID.equals(trainedClassifierID))
					{
						bcs.add(bc);
					}
				}
			}
		}

		final INBC nbc = new NetworkBinaryClassifiers(errorClassName, bcs);

		return nbc;
	}
}
