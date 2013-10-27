package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.core.javafx.ACommand;

public class InitPredictionFolderCommand extends ACommand<Path> {

	private final Path predictionFolder;
	
	@Inject
	public InitPredictionFolderCommand(@Assisted Path predictionFolder) {
		this.predictionFolder = predictionFolder;
	}

	@Override
	protected Path call() throws Exception {
		if (Files.notExists(predictionFolder) || !Files.isDirectory(predictionFolder)) Files.createDirectories(predictionFolder);
		
		return predictionFolder;
	}

}
