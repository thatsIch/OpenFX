package de.thatsich.openfx.prediction.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.core.IEntityConfig;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.IPredictionState;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPredictionConfig;
import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class NetworkPredictionFileStorageService extends AFileStorageService<INetworkPrediction>
{
	@Inject
	protected NetworkPredictionFileStorageService(IPredictionState state)
	{
		super(state.path().get());
	}

	@Override
	public List<INetworkPrediction> init() throws IOException
	{
		final List<INetworkPrediction> binaryPredictions = new LinkedList<>();

		// traverse whole directory and search for yaml files
		// try to open them
		// and parse the correct classifier
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath))
		{
			for (Path child : stream)
			{
				final INetworkPrediction prediction = this.retrieve(child);
				binaryPredictions.add(prediction);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All NetworkPredictions extracted.");

		return binaryPredictions;
	}

	@Override
	public INetworkPrediction create(INetworkPrediction prediction)
	{
		final NetworkPredictionConfig config = prediction.getConfig();
		final String fileName = config.toString();
		final Path dirPath = super.storagePath.resolve(fileName);
		super.createInvalidDirectory(dirPath);

		final Mat modified = prediction.modified().get();
		Images.store(modified, dirPath.resolve("modified.png"));

		return prediction;
	}

	@Override
	public INetworkPrediction retrieve(Path filePath)
	{
		final String fileName = filePath.getFileName().toString();

		final NetworkPredictionConfig config = new NetworkPredictionConfig(fileName);
		final Mat modified = Images.toMat(filePath.resolve("modified.png"));

		return new NetworkPrediction(config, modified);
	}

	@Override
	public INetworkPrediction update(final INetworkPrediction elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(final INetworkPrediction prediction) throws IOException
	{
		final IEntityConfig config = prediction.getConfig();
		final String fileName = config.toString();
		final Path path = super.storagePath.resolve(fileName);

		super.deleteRecursively(path);
	}
}
