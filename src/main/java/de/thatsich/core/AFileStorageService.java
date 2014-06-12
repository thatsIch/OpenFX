package de.thatsich.core;

import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public abstract class AFileStorageService<T> implements IFileStorageService<T>
{
	public final Path storagePath;

	@Inject public Log log;

	protected AFileStorageService(Path storagePath)
	{
		this.storagePath = storagePath;
		this.createInvalidDirectory(storagePath);
	}

	/**
	 * checks if either the directory exists and if its a directory
	 *
	 * @param directory to be created directory
	 */
	protected void createInvalidDirectory(Path directory)
	{
		if (Files.notExists(directory) || !Files.isDirectory(directory))
		{
			try
			{
				Files.createDirectories(directory);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	protected String getFileNameWithoutExtension(Path path)
	{
		final Path file = path.getFileName();
		final String fileName = file.toString();
		final int index = fileName.lastIndexOf('.');

		return (index > 0) ? fileName.substring(0, index) : "";
	}

	protected void deleteRecursively(Path path) throws IOException
	{
		Files.walkFileTree(path, new SimpleFileVisitor<Path>()
		{
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
			{
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
			{
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
