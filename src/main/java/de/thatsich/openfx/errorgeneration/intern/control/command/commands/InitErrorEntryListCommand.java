package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorStorageService;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


public class InitErrorEntryListCommand extends ACommand<List<ErrorEntry>>
{
	private final Path errorInputFolderPath;
	private final ErrorStorageService storage;

	@Inject
	protected InitErrorEntryListCommand(@Assisted Path errorInputFolderPath, ErrorStorageService storage)
	{
		this.errorInputFolderPath = errorInputFolderPath;
		this.storage = storage;
	}

	@Override
	protected List<ErrorEntry> call() throws Exception
	{
		this.createInvalidDirectory(this.errorInputFolderPath);
		return this.getErrorEntryList(this.errorInputFolderPath);
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
			this.log.info("Created directory " + directory + ".");
		}
	}

	/**
	 * Fetches stream of directory and get all image files
	 *
	 * @param directory to be searched in
	 *
	 * @return all images
	 */
	private List<ErrorEntry> getErrorEntryList(Path directory)
	{
		final List<ErrorEntry> errorEntryList = new LinkedList<>();

		try (DirectoryStream<Path> subs = Files.newDirectoryStream(directory, new DirectoriesFilter()))
		{
			for (Path sub : subs)
			{
				errorEntryList.add(this.storage.load(sub));
				this.log.info("Added " + sub + ".");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + errorEntryList.size() + ".");

		return errorEntryList;
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
