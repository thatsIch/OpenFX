package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.core.javafx.ACommand;

import java.nio.file.Files;
import java.nio.file.Path;


public class RemovePreProcessingCommand extends ACommand<IPreProcessing>
{
	private final IPreProcessing preProcessing;

	@Inject
	public RemovePreProcessingCommand(@Assisted IPreProcessing preProcessing)
	{
		this.preProcessing = preProcessing;
	}

	@Override
	protected IPreProcessing call() throws Exception
	{
		final Path path = preProcessing.getFilePathProperty().get();
		if (Files.exists(path))
		{
			Files.delete(path);
			this.log.info(path + " deleted.");
		}

		return this.preProcessing;
	}
}