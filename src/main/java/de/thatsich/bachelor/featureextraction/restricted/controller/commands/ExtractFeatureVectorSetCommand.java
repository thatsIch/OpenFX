package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.services.CSVService;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;

public class ExtractFeatureVectorSetCommand extends ACommand<FeatureVectorSet> {

	// Properties
	private final ObjectProperty<Path> featureInputFolderPath = new SimpleObjectProperty<Path>();
	private final ObjectProperty<ErrorEntry> errorEntry = new SimpleObjectProperty<ErrorEntry>();
	private final ObjectProperty<IFeatureExtractor> featureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	@Inject
	public ExtractFeatureVectorSetCommand(@Assisted Path folderPath, @Assisted ErrorEntry errorEntry, @Assisted IFeatureExtractor extractor, @Assisted int frameSize) {
		this.featureInputFolderPath.set(folderPath);
		this.errorEntry.set(errorEntry);
		this.featureExtractor.set(extractor);
		this.frameSize.set(frameSize);
	}

	@Override
	protected FeatureVectorSet call() throws Exception {
		final Path folderPath = featureInputFolderPath.get();
		final String className = errorEntry.get().getErrorClassProperty().get();
		final IFeatureExtractor extractor = featureExtractor.get();
		final String extractorName = featureExtractor.get().getName();
		final int size = frameSize.get();
		final String id = UUID.randomUUID().toString();
		log.info("Prepared all necessary information.");
		
		final List<FeatureVector> featureVectorList = FXCollections.observableArrayList();
		final List<List<Float>> csvResult = FXCollections.observableArrayList();
		final Mat[][] originalErrorSplit = Images.split(errorEntry.get().getOriginalWithErrorMat(), size, size);
		final Mat[][] errorSplit = Images.split(errorEntry.get().getErrorMat(), size, size);
		log.info("Prepared split images.");
		
		for (int col = 0; col < originalErrorSplit.length; col++) {
			for (int row = 0; row < originalErrorSplit[col].length; row++) {
				try {
					// extract feature vector
					// and reshape them into a one row feature vector if its 2D mat and removes unecessary channels
					final MatOfFloat featureVector = extractor.extractFeature(originalErrorSplit[col][row]);
					featureVector.reshape(1, 1);
					List<Float> featureVectorAsList = new ArrayList<Float>(featureVector.toList());

					// if contain an error classify it as positive match
					if (Core.sumElems(errorSplit[col][row]).val[0] != 0) {
						featureVectorList.add(new FeatureVector(featureVectorAsList, true));
						featureVectorAsList.add(1F);						
					}
					
					// else its a negative match
					else {
						featureVectorList.add(new FeatureVector(featureVectorAsList, false));
						featureVectorAsList.add(0F);
					}
					
					csvResult.add(featureVectorAsList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(className + "_");
		buffer.append(extractorName + "_");
		buffer.append(size + "_"); 	
		buffer.append(id + ".csv");
		
		final Path filePath = folderPath.resolve(buffer.toString());
		CSVService.write(filePath, csvResult);
		
		log.info("Extracted FeatureVectors: " + featureVectorList.size());
		return new FeatureVectorSet(filePath, className, extractorName, size, id, featureVectorList);
	}
}
