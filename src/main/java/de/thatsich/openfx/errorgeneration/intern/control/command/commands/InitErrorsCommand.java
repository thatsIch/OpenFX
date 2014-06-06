package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


public class InitErrorsCommand extends ACommand<List<IError>>
{
	private final Path path;
	private final ErrorFileStorageService storage;

	@Inject
	protected InitErrorsCommand(IErrorState state, ErrorFileStorageService storage)
	{
		this.path = state.path().get();
		this.storage = storage;
	}

	@Override
	protected List<IError> call() throws Exception
	{
		this.createInvalidDirectory(this.path);
		return this.getErrorEntryList(this.path);
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
	private List<IError> getErrorEntryList(Path directory)
	{
		final List<IError> errorList = new LinkedList<>();

		try (DirectoryStream<Path> subs = Files.newDirectoryStream(directory, new DirectoriesFilter()))
		{
			for (Path sub : subs)
			{
				errorList.add(this.storage.retrieve(sub));
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
