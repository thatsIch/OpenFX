package de.thatsich.bachelor.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.core.javafx.ACommand;

import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteBinaryPredictionCommand extends ACommand<BinaryPrediction>
{

	// Fields
	final BinaryPrediction toBeDeletedBinaryPrediction;

	@Inject
	private DeleteBinaryPredictionCommand(@Assisted BinaryPrediction toBeDeletedBinaryPrediction)
	{
		this.toBeDeletedBinaryPrediction = toBeDeletedBinaryPrediction;
	}

	@Override
	protected BinaryPrediction call() throws Exception
	{
		final Path toBeDeletedFilePath = this.toBeDeletedBinaryPrediction.getFilePathProperty().get();
		if (Files.exists(toBeDeletedFilePath))
		{
			Files.delete(toBeDeletedFilePath);
			this.log.info(toBeDeletedFilePath + " deleted.");
		}
		else
		{
			this.log.warning("Could not delete " + toBeDeletedFilePath + " because it was not found.");
		}

		return this.toBeDeletedBinaryPrediction;
	}

}
