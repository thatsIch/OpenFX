package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageFileStorageService;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class InitImageEntryListCommand extends ACommand<List<IImage>>
{
	private final Path path;
	private final ImageFileStorageService storage;

	@Inject
	protected InitImageEntryListCommand(IImageState state, ImageFileStorageService storage)
	{
		this.storage = storage;
		this.path = state.imageFolder().get();
	}

	@Override
	protected List<IImage> call() throws Exception
	{
		final List<IImage> result = new LinkedList<>();
		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";

		if (Files.notExists(this.path) || !Files.isDirectory(this.path))
		{
			Files.createDirectories(this.path);
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path, GLOB_PATTERN))
		{
			for (Path child : stream)
			{
				final IImage load = this.storage.load(child);

				result.add(load);
				this.log.info("Added " + child);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + result.size() + ".");

		return result;
	}

}
