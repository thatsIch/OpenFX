package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.application.guice.BinaryClassificationProvider;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.BinaryClassifiers;
import de.thatsich.core.javafx.Command;

public class InitBinaryClassificationListCommand extends Command<List<IBinaryClassification>> {
	
	@Inject private BinaryClassificationProvider provider;
	@Inject private BinaryClassifiers binaryClassifiers;
	
	private final ObjectProperty<Path> binaryClassificationFolderPath = new SimpleObjectProperty<Path>();
	
	@Inject
	protected InitBinaryClassificationListCommand(@Assisted Path binaryClassificationFolderPath) {
		this.binaryClassificationFolderPath.set(binaryClassificationFolderPath);
	}
	
	@Override
	protected Task<List<IBinaryClassification>> createTask() {
		return new Task<List<IBinaryClassification>>() {
			@Override protected List<IBinaryClassification> call() throws Exception {
				final Path folderPath = binaryClassificationFolderPath.get(); 
				final List<IBinaryClassification> binaryClassificationList = FXCollections.observableArrayList();
				
				if (Files.notExists(folderPath) || !Files.isDirectory(folderPath)) Files.createDirectories(folderPath);
				
				final String GLOB_PATTERN = "*.{yaml}";
				
				// traverse whole directory and search for yaml files
				// try to open them
				// and parse the correct classifier
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, GLOB_PATTERN)) {
					for (Path child : stream) {
						
						// split the file name 
						// and check if has 5 members
						// and extract them
						final String fileName = child.getFileName().toString();
						final String[] fileNameSplit = fileName.split("_");
						if (fileNameSplit.length != 5) throw new WrongNumberArgsException("Expected 5 encoded information but found " + fileNameSplit.length);
						
						final String classifierName = fileNameSplit[0];
						final String extractorName = fileNameSplit[1];
						final int frameSize = Integer.parseInt(fileNameSplit[2]);
						final String errorName = fileNameSplit[3];
						final String id = fileNameSplit[4];
						
						for (IBinaryClassifier classifier : binaryClassifiers.getBinaryClassifierListProperty()) {
							if (classifierName.equals(classifier.getName())) {
								
								break;
							}
						}
						
//						provider.c
//						featureVectorSetList.add(new FeatureVectorSet(child, className, extractorName, frameSize, id, featureVectorList));
						log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
					}
				} catch (IOException | DirectoryIteratorException e) {
					e.printStackTrace();
				}
				log.info("All FeatureVectors added.");
				
				return binaryClassificationList;
			}
		};
	}

}
