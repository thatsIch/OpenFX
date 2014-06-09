package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.api.control.entity.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingFileStorageService;
import de.thatsich.openfx.preprocessing.intern.model.PreProcessingState;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class InitPreProcessingsCommand extends ACommand<List<IPreProcessing>>
{
	// Fields
	private final Path path;
	private final PreProcessingFileStorageService storage;

	@Inject
	protected InitPreProcessingsCommand(PreProcessingState state, PreProcessingFileStorageService storage)
	{
		this.path = state.path().get();
		this.storage = storage;
	}

	@Override
	protected List<IPreProcessing> call() throws Exception
	{
		final List<IPreProcessing> list = FXCollections.observableArrayList();

		if (Files.notExists(this.path) || !Files.isDirectory(this.path))
		{
			Files.createDirectories(this.path);
		}

		// traverse whole directory and search for pp files
		// try to open them
		// and parse the correct preprocessor
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path))
		{
			for (Path child : stream)
			{
				final IPreProcessing retrieve = this.storage.retrieve(child);

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
}
