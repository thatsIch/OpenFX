package de.thatsich.bachelor.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.classification.api.model.IBinaryClassification;
import de.thatsich.core.javafx.ACommand;

import java.nio.file.Files;
import java.nio.file.Path;

public class RemoveBinaryClassificationCommand extends ACommand<IBinaryClassification>
{

	final private IBinaryClassification binaryClassification;

	@Inject
	public RemoveBinaryClassificationCommand(@Assisted IBinaryClassification binaryClassification)
	{
		this.binaryClassification = binaryClassification;
	}

	@Override
	protected IBinaryClassification call() throws Exception
	{
		final Path path = binaryClassification.getFilePathProperty().get();
		if (Files.exists(path))
		{
			Files.delete(path);
			this.log.info("File deleted.");
		}

		return this.binaryClassification;
	}
}