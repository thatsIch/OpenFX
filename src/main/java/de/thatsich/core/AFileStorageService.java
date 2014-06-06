package de.thatsich.core;

import com.google.inject.Inject;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public abstract class AFileStorageService<T> implements IFileStorageService<T>
{
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	public final Path storagePath;

	@Inject public Log log;

	protected AFileStorageService(Path storagePath)
	{
		this.storagePath = storagePath;
	}

	protected String getUniqueID()
	{
		return UUID.randomUUID().toString();
	}

	protected String getTimeStamp()
	{
		return format.format(new Date());
	}

	protected String getFileNameWithoutExtension(Path path)
	{
		final Path file = path.getFileName();
		final String fileName = file.toString();
		final int index = fileName.lastIndexOf('.');

		return (index > 0) ? fileName.substring(0, index) : "";
	}

	/**
	 * checks if either the directory exists and if its a directory
	 *
	 * @param directory to be created directory
	 */
	protected void createInvalidDirectory(Path directory) throws IOException
	{
		if (Files.notExists(directory) || !Files.isDirectory(directory))
		{
			Files.createDirectories(directory);
		}
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
