package de.thatsich.openfx.errorgeneration.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.intern.control.entity.Error;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


public class ErrorFileStorageService extends AFileStorageService<IError>
{
	@Inject
	protected ErrorFileStorageService(IErrorState state)
	{
		super(state.path().get());
	}

	@Override
	public List<IError> init() throws IOException
	{
		final List<IError> errorList = new LinkedList<>();

		try (DirectoryStream<Path> subs = Files.newDirectoryStream(this.storagePath, new DirectoriesFilter()))
		{
			for (Path sub : subs)
			{
				errorList.add(this.retrieve(sub));
				this.log.info("Added " + sub + ".");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + errorList.size() + ".");

		return errorList;
	}

	@Override
	public IError create(final IError error) throws IOException
	{
		final ErrorConfig config = error.getConfig();
		final String fileName = config.toString();
		final Path path = super.storagePath.resolve(fileName);

		Images.store(error.originalProperty().get(), path.resolve("original.png"));
		Images.store(error.modifiedProperty().get(), path.resolve("modified.png"));
		Images.store(error.errorProperty().get(), path.resolve("error.png"));

		return error;
	}

	@Override
	public IError retrieve(final Path path) throws IOException
	{
		final String fileName = path.getFileName().toString();

		final ErrorConfig config = new ErrorConfig(fileName);
		final Mat original = Images.toMat(path.resolve("original.png"));
		final Mat modified = Images.toMat(path.resolve("modified.png"));
		final Mat error = Images.toMat(path.resolve("error.png"));

		return new Error(config, original, modified, error);
	}

	@Override
	public IError update(final IError elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(final IError elem) throws IOException
	{
		final ErrorConfig config = elem.getConfig();
		final String fileName = config.toString();
		final Path path = super.storagePath.resolve(fileName);

		this.deleteRecursively(path);
	}

	/**
	 * Filter to get only directories
	 */
	private class DirectoriesFilter implements DirectoryStream.Filter<Path>
	{
		@Override
		public boolean accept(final Path path) throws IOException
		{
			return Files.isDirectory(path);
		}
	}
}
