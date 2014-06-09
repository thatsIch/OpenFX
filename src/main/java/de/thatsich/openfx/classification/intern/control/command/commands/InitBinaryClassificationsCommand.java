package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IClassificationState;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;


public class InitBinaryClassificationsCommand extends ACommand<List<IBinaryClassification>>
{
	private final Path path;
	private final ClassificationFileStorageService storage;

	@Inject
	protected InitBinaryClassificationsCommand(IClassificationState state, ClassificationFileStorageService storage)
	{
		this.storage = storage;
		this.path = state.path().get();
	}

	@Override
	protected List<IBinaryClassification> call() throws Exception
	{
		final List<IBinaryClassification> classifications = new LinkedList<>();

		if (Files.notExists(this.path) || !Files.isDirectory(this.path))
		{
			Files.createDirectories(this.path);
		}

		// traverse whole directory and search for yaml files
		// try to open them
		// and parse the correct classifier
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path))
		{
			for (Path child : stream)
			{
				System.out.println("Found " + child);
				final IBinaryClassification bc = this.storage.retrieve(child);
				classifications.add(bc);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All BinaryClassification added.");

		return classifications;
	}

}
