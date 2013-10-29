package de.thatsich.bachelor.featureextraction.restricted.command.commands;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;

import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.services.CSVService;
import de.thatsich.core.javafx.ACommand;

public class InitFeatureVectorSetListCommand extends ACommand<List<FeatureVectorSet>> {

	// Fields
	private final ObjectProperty<Path> featureVectorFolderPath = new SimpleObjectProperty<Path>();
	
	// Injects
	@Inject private CSVService csv;
	
	@Inject
	protected InitFeatureVectorSetListCommand(@Assisted Path featureVectorFolderPath) {
		this.featureVectorFolderPath.set(featureVectorFolderPath);
	}

	@Override
	protected List<FeatureVectorSet> call() throws Exception {
		final Path folderPath = featureVectorFolderPath.get(); 
		final List<FeatureVectorSet> featureVectorSetList = new ArrayList<FeatureVectorSet>();
		
		if (Files.notExists(folderPath) || !Files.isDirectory(folderPath)) Files.createDirectories(folderPath);
		
		final String GLOB_PATTERN = "*.{csv}";
		
		// traverse whole directory and search for csv files
		// try to open them
		// and read each csv file
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				
				// split the file name 
				// and check if has 4 members
				// and extract them
				final String fileName = child.getFileName().toString();
				final String[] fileNameSplit = fileName.split("_");
				if (fileNameSplit.length != 4) throw new WrongNumberArgsException("Expected 4 encoded information but found " + fileNameSplit.length);
				List<Float[]> floatValues = csv.read(child);
				final String className = fileNameSplit[0];
				final String extractorName = fileNameSplit[1];
				final int frameSize = Integer.parseInt(fileNameSplit[2]);
				final String id = fileNameSplit[3];
				
				// read each line
				// and store them as a single FeatureVector
				// in the end write all information into the FeatureVectorSet
				final List<FeatureVector> featureVectorList = FXCollections.observableArrayList();
				for (Float[] floatArray : floatValues) {
					final List<Float> vector = FXCollections.<Float>observableArrayList(floatArray);
					final Float label = vector.get(vector.size() - 1);
					vector.remove(vector.size() - 1);
					
					final boolean boolLabel = (label > 0);
					featureVectorList.add(new FeatureVector(vector, boolLabel));
				}
				
				featureVectorSetList.add(new FeatureVectorSet(child, className, extractorName, frameSize, id, featureVectorList));
				log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		log.info("All FeatureVectors added.");
		
		return featureVectorSetList;
	}

}
