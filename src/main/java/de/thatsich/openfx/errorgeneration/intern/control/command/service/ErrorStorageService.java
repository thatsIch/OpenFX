package de.thatsich.openfx.errorgeneration.intern.control.command.service;

import de.thatsich.core.IFileStorageService;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ErrorStorageService implements IFileStorageService<ErrorEntry>
{
	@Override
	public void save(final ErrorEntry entry) throws IOException
	{
		final Path path = entry.path();
		this.createInvalidDirectory(entry.path());

		Images.store(entry.getOriginalMat(), path.resolve("original.png"));
		Images.store(entry.getOriginalWithErrorMat(), path.resolve("modified.png"));
		Images.store(entry.getErrorMat(), path.resolve("error.png"));
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
	public ErrorEntry load(final Path path) throws IOException
	{
		final String unparsedString = path.getFileName().toString();
		final String splitString[] = unparsedString.split("_");
		final String date = splitString[0];
		final String className = splitString[1];
		final String id = splitString[2];

		final Mat original = Images.toMat(path.resolve("original.png"));
		final Mat modified = Images.toMat(path.resolve("modified.png"));
		final Mat error = Images.toMat(path.resolve("error.png"));

		return new ErrorEntry(path, original, modified, error, className, id);
	}
}
