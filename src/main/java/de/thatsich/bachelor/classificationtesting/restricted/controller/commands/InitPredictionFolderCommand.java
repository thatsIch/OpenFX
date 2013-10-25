package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.core.javafx.Command;

public class InitPredictionFolderCommand extends Command<Path> {

	private final Path predictionFolder;
	
	@Inject
	public InitPredictionFolderCommand(@Assisted Path predictionFolder) {
		this.predictionFolder = predictionFolder;
	}
	
	@Override
	protected Task<Path> createTask() {
		return new Task<Path>() {

			@Override
			protected Path call() throws Exception {
				if (Files.notExists(predictionFolder) || !Files.isDirectory(predictionFolder)) Files.createDirectories(predictionFolder);
				
				return predictionFolder;
			}
		};
	}

}
