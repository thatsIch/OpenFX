package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.BinaryPredictionFileStorageService;
import de.thatsich.core.javafx.ACommand;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InitBinaryPredictionListCommand extends ACommand<List<BinaryPrediction>>
{

	// Fields
	private final Path binaryPredictionFolderPath;

	// Injects
	@Inject BinaryPredictionFileStorageService fileStorage;

	@Inject
	protected InitBinaryPredictionListCommand(@Assisted Path binaryPredictionFolderPath)
	{
		this.binaryPredictionFolderPath = binaryPredictionFolderPath;
	}

	@Override
	protected List<BinaryPrediction> call() throws Exception
	{
		final List<BinaryPrediction> binaryPredictionList = FXCollections.observableArrayList();

		final String GLOB_PATTERN = "*.{png}";

		// traverse whole directory and search for yaml files
		// try to open them
		// and parse the correct classifier
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(binaryPredictionFolderPath, GLOB_PATTERN))
		{
			for (Path child : stream)
			{
				final BinaryPrediction prediction = fileStorage.load(child);
				binaryPredictionList.add(prediction);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		log.info("All BinaryClassification extracted.");

		return binaryPredictionList;
	}

}