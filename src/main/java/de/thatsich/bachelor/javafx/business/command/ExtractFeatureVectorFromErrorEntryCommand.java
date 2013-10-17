package de.thatsich.bachelor.javafx.business.command;

import java.util.List;
import java.util.UUID;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IFeatureExtractor;
import de.thatsich.core.opencv.Images;

public class ExtractFeatureVectorFromErrorEntryCommand extends Command<List<FeatureVector>> {

	// Properties
	private final ObjectProperty<ErrorEntry> errorEntry = new SimpleObjectProperty<ErrorEntry>();
	private final ObjectProperty<IFeatureExtractor> featureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	@Inject
	public ExtractFeatureVectorFromErrorEntryCommand(@Assisted ErrorEntry errorEntry, @Assisted IFeatureExtractor extractor, @Assisted int frameSize) {
		this.errorEntry.set(errorEntry);
		this.featureExtractor.set(extractor);
		this.frameSize.set(frameSize);
	}
	
	@Override
	protected Task<List<FeatureVector>> createTask() {
		return new Task<List<FeatureVector>>() {
			
			@Override
			protected List<FeatureVector> call() throws Exception {
				final String className = errorEntry.get().getErrorClassProperty().get();
				final IFeatureExtractor extractor = featureExtractor.get();
				final String extractorName = featureExtractor.get().getName();
				final int size = frameSize.get();
				final String id = UUID.randomUUID().toString();
				log.info("Prepared all necessary information.");
				
				final List<FeatureVector> result = FXCollections.observableArrayList();
//				final List<List<Float>> csvResult = FXCollections.observableArrayList();
				final Mat[][] originalErrorSplit = Images.split(errorEntry.get().getOriginalWithErrorMat(), size, size);
				final Mat[][] errorSplit = Images.split(errorEntry.get().getErrorMat(), size, size);
				
//				log.info("" + originalErrorSplit.length);
				
				for (int col = 0; col < originalErrorSplit.length; col++) {
					for (int row = 0; row < originalErrorSplit[col].length; row++) {
						final MatOfFloat featureVector = extractor.extractFeature(originalErrorSplit[col][row]);
//						System.out.println(featureVector.toString());
//						float[] first = featureVector.toArray();
//						float[] test = Arrays.copyOf(first, first.length + 1); 
						
						
						// if contain an error classify it as positive match
						if (Core.sumElems(errorSplit[col][row]).val[0] != 0) {
							result.add(new FeatureVector(className, extractorName, size, id, featureVector, new MatOfFloat(1)));
//							test[test.length - 1] = 1; 
//							positiveFeatureMat.push_back(featureVector.t());
//							List<Float> featureVectorAsList = featureVector.toList();
//							featureVectorAsList.add(1F);
//							csvResult.add(featureVectorAsList);
//							Arrays.
						}
						
						// else its a negative match
						else {
							result.add(new FeatureVector(className, extractorName, size, id, featureVector, new MatOfFloat(0)));
//							test[test.length - 1] = 0;
//							negativeFeatureMat.push_back(featureVector.t());
//							List<Float> featureVectorAsList = featureVector.toList();
//							featureVectorAsList.add(0F);
//							csvResult.add(featureVectorAsList);
						}
					}
				}
				
				StringBuffer buffer = new StringBuffer();
				buffer.append(className + "_");
				buffer.append(extractorName + "_");
				buffer.append(size + "_"); 	
				buffer.append(id);
				
//				CSVService.write(Paths.get(buffer.toString()), csvResult);
				
				log.info("Extracted FeatureVectors: " + result.size());
				return result;
			}
		};
	}
}
