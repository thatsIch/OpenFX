package de.thatsich.core;

import com.google.inject.Inject;

import java.nio.file.Path;
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
}
