package de.thatsich.bachelor.imageprocessing.restricted.controller.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import de.thatsich.core.javafx.ACommand;

public class CopyFileCommand extends ACommand<Path> {
	
	// Properties
	final private ObjectProperty<Path> originPath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<Path> copyPath = new SimpleObjectProperty<Path>();
	
	@AssistedInject
	public CopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath) {
		this.originPath.set(originPath);
		this.copyPath.set(copyPath);
	}

	@Override
	protected Path call() throws IOException {
		if (originPath.get() == null || copyPath.get() == null) throw new CancellationException("Command not initialized properly.");
		
		if (Files.exists(copyPath.get())) {
			log.info("Duplicate found: File already exists.");
			this.cancel();
			return null;
		}
		else {
			log.info("Copying File.");
			return Files.copy(originPath.get(), copyPath.get());
		}
	}
}
