package de.thatsich.bachelor.preprocessing.intern.command.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.core.javafx.ACommand;

public class RemovePreProcessingCommand extends ACommand<IBinaryClassification> {

	final private IBinaryClassification binaryClassification;
	
	@Inject
	public RemovePreProcessingCommand(@Assisted IBinaryClassification binaryClassification) {
		this.binaryClassification = binaryClassification;
	}

	@Override
	protected IBinaryClassification call() throws Exception {
		final Path path = binaryClassification.getFilePathProperty().get();
		if (Files.exists(path)) {
			Files.delete(path);
			this.log.info("File deleted.");
		}
		
		return this.binaryClassification;
	}
}