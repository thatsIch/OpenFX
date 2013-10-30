package de.thatsich.bachelor.classification.intern.command.commands;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.collections.FXCollections;

import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.BinaryClassificationProvider;
import de.thatsich.bachelor.classification.intern.command.classifier.BinaryClassifierConfiguration;
import de.thatsich.core.javafx.ACommand;

public class InitBinaryClassificationListCommand extends ACommand<List<IBinaryClassification>> {
	
	@Inject private BinaryClassificationProvider provider;
	
	private final Path binaryClassificationFolderPath;
	
	@Inject
	protected InitBinaryClassificationListCommand(@Assisted Path binaryClassificationFolderPath) {
		this.binaryClassificationFolderPath = binaryClassificationFolderPath;
	}

	@Override
	protected List<IBinaryClassification> call() throws Exception {
		final List<IBinaryClassification> binaryClassificationList = FXCollections.observableArrayList();
		
		if (Files.notExists(this.binaryClassificationFolderPath) || !Files.isDirectory(this.binaryClassificationFolderPath)) Files.createDirectories(this.binaryClassificationFolderPath);
		
		final String GLOB_PATTERN = "*.{yaml}";
		
		// traverse whole directory and search for yaml files
		// try to open them
		// and parse the correct classifier
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.binaryClassificationFolderPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				try {
				// split the file name 
				// and check if has 5 members
				// and extract them
				final String fileName = child.getFileName().toString();
				final String[] fileNameSplit = fileName.split("_");
				if (fileNameSplit.length != 5) throw new WrongNumberArgsException("Expected 5 encoded information but found " + fileNameSplit.length);
				this.log.info("Split FileNmae.");
				
				final String classificationName = fileNameSplit[0];
				final String extractorName = fileNameSplit[1];
				final int frameSize = Integer.parseInt(fileNameSplit[2]);
				final String errorName = fileNameSplit[3];
				final String id = fileNameSplit[4];
				this.log.info("Prepared SubInformation.");
				
				final BinaryClassifierConfiguration config = new BinaryClassifierConfiguration(child, classificationName, extractorName, frameSize, errorName, id);
				final IBinaryClassification classification;
				switch(classificationName) {
					case "RandomForestBinaryClassifier":
						classification = provider.createRandomForestBinaryClassification(new CvRTrees(), config);
						break;
					case "SVMBinaryClassifier":
						classification = provider.createSVMBinaryClassification(new CvSVM(), config);
						break;
					default:
						throw new IllegalStateException("Unknown Classification");
				}
				this.log.info("Resolved BinaryClassification.");
				
				classification.load(child.toAbsolutePath().toString());
				this.log.info("Loaded YAML into BinaryClassification.");

				binaryClassificationList.add(classification);
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All BinaryClassification added.");
		
		return binaryClassificationList;
	}

}