package de.thatsich.openfx.preprocessing.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.preprocessing.api.control.entity.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessingState;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.AANNPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.IdentityPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 06.06.2014.
 */
public class PreProcessingFileStorageService extends AFileStorageService<IPreProcessing>
{
	private static final String AANN_EXT = ".aann";
	private static final String ID_EXT = ".id";

	@Inject
	protected PreProcessingFileStorageService(IPreProcessingState state)
	{
		super(state.path().get());
	}

	@Override
	public List<IPreProcessing> init() throws IOException
	{
		final List<IPreProcessing> list = new LinkedList<>();

		// traverse whole directory and search for pp files
		// try to open them
		// and parse the correct preprocessor
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath))
		{
			for (Path child : stream)
			{
				final IPreProcessing retrieve = this.retrieve(child);

				list.add(retrieve);
				this.log.info("Added " + retrieve);
			} // END FOR
		} // END OUTER TRY
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All PreProcessings added.");

		return list;
	}

	@Override
	public IPreProcessing create(IPreProcessing elem) throws IOException
	{
		final String fileName = elem.getConfig().toString();
		final String withExtension;
		if (elem instanceof AANNPreProcessing)
		{
			withExtension = fileName + AANN_EXT;
		}
		else
		{
			withExtension = fileName + ID_EXT;
		}

		final Path filePath = this.storagePath.resolve(withExtension);
		final File file = filePath.toFile();

		try
		{
			EncogDirectoryPersistence.saveObject(file, elem.networkProperty().get());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.log.info("Saved PreProcessing.");

		return elem;
	}

	@Override
	public IPreProcessing retrieve(Path path) throws IOException
	{
		final Path filePath = path.getFileName();
		final String fileName = filePath.toString();
		final File file = path.toAbsolutePath().toFile();

		final BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence.loadObject(file);
		final String withoutExt = this.getFileNameWithoutExtension(path);
		final PreProcessingConfig config = new PreProcessingConfig(withoutExt);

		if (fileName.endsWith(AANN_EXT))
		{
			return new AANNPreProcessing(network, config);
		}
		else
		{
			return new IdentityPreProcessing(network, config);
		}
	}

	@Override
	public IPreProcessing update(IPreProcessing elem) throws IOException
	{
		return null;
	}

	@Override
	public void delete(IPreProcessing elem) throws IOException
	{
		final String fileName;
		if (elem instanceof AANNPreProcessing)
		{
			fileName = elem.getConfig().toString() + AANN_EXT;
		}
		else
		{
			fileName = elem.getConfig().toString() + ID_EXT;
		}

		final Path filePath = super.storagePath.resolve(fileName);
		if (Files.exists(filePath))
		{
			Files.delete(filePath);
			this.log.info("Deleted " + filePath);
		}
	}
}
