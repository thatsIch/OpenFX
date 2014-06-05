package de.thatsich.openfx.errorgeneration.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.intern.control.entity.Error;
import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;


public class ErrorFileStorageService extends AFileStorageService<IError>
{
	@Inject
	protected ErrorFileStorageService(IErrorState state)
	{
		super(state.path().get());
	}

	@Override
	public void save(final IError elem) throws IOException
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(elem.dateTimeProperty().get());
		joiner.add(elem.clazzProperty().get());
		joiner.add(elem.idProperty().get());
		final String fileName = joiner.toString();
		final Path path = super.storagePath.resolve(fileName);

		this.createInvalidDirectory(path);

		Images.store(elem.originalProperty().get(), path.resolve("original.png"));
		Images.store(elem.modifiedProperty().get(), path.resolve("modified.png"));
		Images.store(elem.errorProperty().get(), path.resolve("error.png"));
	}

	/**
	 * checks if either the directory exists and if its a directory
	 *
	 * @param directory to be created directory
	 */
	private void createInvalidDirectory(Path directory) throws IOException
	{
		if (Files.notExists(directory) || !Files.isDirectory(directory))
		{
			Files.createDirectories(directory);
		}
	}

	@Override
	public IError load(final Path path) throws IOException
	{
		final String unparsedString = path.getFileName().toString();
		final String splitString[] = unparsedString.split("_");
		final String date = splitString[0];
		final String className = splitString[1];
		final String id = splitString[2];

		final Mat original = Images.toMat(path.resolve("original.png"));
		final Mat modified = Images.toMat(path.resolve("modified.png"));
		final Mat error = Images.toMat(path.resolve("error.png"));

		return new Error(original, modified, error, date, className, id);
	}

	@Override
	public void update(final IError elem) throws IOException
	{

	}

	@Override
	public void delete(final IError elem) throws IOException
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(elem.dateTimeProperty().get());
		joiner.add(elem.clazzProperty().get());
		joiner.add(elem.idProperty().get());
		final String fileName = joiner.toString();
		final Path path = super.storagePath.resolve(fileName);

		this.deleteChildren(path);
		this.deletePath(path);
	}

	private void deleteChildren(Path parent)
	{
		try (DirectoryStream<Path> children = Files.newDirectoryStream(parent))
		{
			for (Path child : children)
			{
				this.deletePath(child);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void deletePath(Path path)
	{
		try
		{
			Files.deleteIfExists(path);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
