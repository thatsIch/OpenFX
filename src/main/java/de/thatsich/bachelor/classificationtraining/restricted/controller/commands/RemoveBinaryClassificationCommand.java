package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.core.javafx.ACommand;

public class RemoveBinaryClassificationCommand extends ACommand<IBinaryClassification> {

	final private ObjectProperty<IBinaryClassification> binaryClassification = new SimpleObjectProperty<IBinaryClassification>();
	
	@Inject
	public RemoveBinaryClassificationCommand(@Assisted IBinaryClassification binaryClassification) {
		this.binaryClassification.set(binaryClassification);
	}

	@Override
	protected IBinaryClassification call() throws Exception {
		Path path = binaryClassification.get().getFilePathProperty().get();
		if (Files.exists(path)) {
			Files.delete(path);
			log.info("File deleted.");
		}
		
		return binaryClassification.get();
	}
}