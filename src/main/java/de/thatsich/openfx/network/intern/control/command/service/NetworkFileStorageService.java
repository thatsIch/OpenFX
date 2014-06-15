package de.thatsich.openfx.network.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworkState;
import de.thatsich.openfx.network.intern.control.prediction.NetworkConfig;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;

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
	private final List<ITrainedBinaryClassifier> classifiers;
	private final INetworkCommandProvider networkProvider;

	@Inject
	NetworkFileStorageService(INetworkState state, IFeatureExtractors featureExtractors, ClassificationFileStorageService service, INetworkCommandProvider networkProvider) throws IOException
	{
		super(state.path().get());

		this.featureExtractors = featureExtractors;
		this.classifiers = service.init();
		this.networkProvider = networkProvider;
	}

	@Override
	public List<ITrainedNetwork> init() throws IOException
	{
		this.log.info("Initiating networks.");
		final List<ITrainedNetwork> networks = new LinkedList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath))
		{
			for (Path child : stream)
			{
				this.log.info("Found potential network in " + child.getFileName().toString());
				final ITrainedNetwork network = this.retrieve(child);
				networks.add(network);
				this.log.info("Added network " + network + " to position " + networks.size());
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
			final StringJoiner joiner = new StringJoiner(System.lineSeparator());
			final List<ITrainedBinaryClassifier> trainedBinaryClassifiers = nbc.getTrainedBinaryClassifier();
			this.log.info("Trained Binary Classifier Size for Network: " + trainedBinaryClassifiers.size());
			for (ITrainedBinaryClassifier bc : trainedBinaryClassifiers)
			{
				final String bcID = bc.getConfig().toString();
				joiner.add(bcID);
				this.log.info("Joined " + bcID);
			}

			try (final BufferedWriter writer = Files.newBufferedWriter(nbcPath, StandardCharsets.US_ASCII))
			{
				writer.write(joiner.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
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
				final String uniqueErrorClassName = inbc.getUniqueErrorClassName();
				uniqueErrorClassNames.add(uniqueErrorClassName);
				this.log.info("Retrieved NBC " + inbc + " with errorClass " + uniqueErrorClassName);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}

		final ICNBC cnbc = this.networkProvider.createCollectiveNetworkBinaryClassifiers(nbcs, uniqueErrorClassNames);
		final ITrainedNetwork trained = this.networkProvider.createTrainedNetwork(config, this.featureExtractors.list(), cnbc);
		this.log.info("Retrieved CNBC with " + cnbc.getNbcs().size() + " NBCs.");

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
		this.deleteRecursively(filePath);
		this.log.info("Deleted Path " + filePath + "with Children.");
	}

	private INBC retrieveNBC(Path path) throws IOException
	{
		final String fileName = path.getFileName().toString();
		final ErrorConfig config = new ErrorConfig(fileName);
		final String errorClassName = config.clazz.get();

		final List<ITrainedBinaryClassifier> bcs = new LinkedList<>();
		try (final BufferedReader vectorReader = Files.newBufferedReader(path, StandardCharsets.US_ASCII))
		{
			String trainedClassifierID;
			while ((trainedClassifierID = vectorReader.readLine()) != null)
			{
				this.log.info("Trying to find " + trainedClassifierID + " in BCs of size " + this.classifiers.size());
				for (ITrainedBinaryClassifier bc : this.classifiers)
				{
					final String bcID = bc.getConfig().toString();
					this.log.info("Comparing " + trainedClassifierID + " with " + bcID);
					if (bcID.equals(trainedClassifierID))
					{
						bcs.add(bc);
						this.log.info("Found matching BC.");
					}
				}
			}
		}

		final INBC nbc = this.networkProvider.createNetworkBinaryClassifiers(errorClassName, bcs);

		return nbc;
	}
}
