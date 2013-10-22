package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import de.thatsich.bachelor.classificationtraining.api.entities.TrainedBinaryClassifier;
import de.thatsich.core.javafx.Command;

public class InitTrainedBinaryClassifierListCommand extends Command<List<TrainedBinaryClassifier>> {
	
	private final ObjectProperty<Path> trainedBinaryClassifierFolderPath = new SimpleObjectProperty<Path>();
	
	@Inject
	protected InitTrainedBinaryClassifierListCommand(@Assisted Path trainedBinaryClassifierFolderPath) {
		this.trainedBinaryClassifierFolderPath.set(trainedBinaryClassifierFolderPath);
	}
	
	@Override
	protected Task<List<TrainedBinaryClassifier>> createTask() {
		return new Task<List<TrainedBinaryClassifier>>() {
			@Override protected List<TrainedBinaryClassifier> call() throws Exception {
				final Path folderPath = trainedBinaryClassifierFolderPath.get(); 
				final List<TrainedBinaryClassifier> trainedBinaryClassifierList = FXCollections.observableArrayList();
				
				if (Files.notExists(folderPath) || !Files.isDirectory(folderPath)) Files.createDirectories(folderPath);
				
				return trainedBinaryClassifierList;
			}
		};
	}

}
