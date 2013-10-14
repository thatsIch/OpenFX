package de.thatsich.bachelor.javafx.business.command;

import java.util.List;
import java.util.UUID;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

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

public class ExtractFeatureVectorCommand extends Command<List<FeatureVector>> {

	// Properties
	private final ObjectProperty<ErrorEntry> errorEntry = new SimpleObjectProperty<ErrorEntry>();
	private final ObjectProperty<IFeatureExtractor> featureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	@Inject
	public ExtractFeatureVectorCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted ErrorEntry errorEntry, @Assisted IFeatureExtractor extractor, @Assisted int frameSize) {
		super(handler);
		this.errorEntry.set(errorEntry);
		this.featureExtractor.set(extractor);
		this.frameSize.set(frameSize);
	}
	
	@Override
	protected Task<List<FeatureVector>> createTask() {
		return new Task<List<FeatureVector>>() {

			// TODO Label richtig berechnen
			// TODO über ganzes Bild iteratieren
			// TODO wahrscheinlich Randfälle weglassen, weil nicht gut
			
			@Override
			protected List<FeatureVector> call() throws Exception {
				final String className = errorEntry.get().getErrorClassProperty().get();
				final String extractorName = featureExtractor.get().getName();
				final int size = frameSize.get();
				final String id = UUID.randomUUID().toString();
//				final MatOfFloat featureLabel = new MatOfFloat(1);
//				final MatOfFloat featureVectorMat = featureExtractor.get().extractFeature(errorEntry.get().getOriginalWithErrorMat());
				log.info("Prepared all necessary information.");
				
				List<FeatureVector> result = FXCollections.observableArrayList();
				
//				MatOfFloat positiveFeatureMat = new MatOfFloat();
//				MatOfFloat negativeFeatureMat = new MatOfFloat();
				
				Mat[][] originalErrorSplit = Images.split(errorEntry.get().getOriginalWithErrorMat(), size, size);
				Mat[][] errorSplit = Images.split(errorEntry.get().getErrorMat(), size, size);
				
				log.info("" + originalErrorSplit.length);
				
				for (int col = 0; col < originalErrorSplit.length; col++) {
					log.info("Col: " + col);
					for (int row = 0; row < originalErrorSplit[col].length; row++) {
						log.info("Row: " + row);
						MatOfFloat featureVector = featureExtractor.get().extractFeature(originalErrorSplit[col][row]);

						// if contain an error classify it as positive match
						if (Core.sumElems(errorSplit[col][row]).val[0] != 0) {
							result.add(new FeatureVector(className, extractorName, size, id, featureVector, new MatOfFloat(1)));
//							positiveFeatureMat.push_back(featureVector.t());
						}
						
						// else its a negative match
						else {
							result.add(new FeatureVector(className, extractorName, size, id, featureVector, new MatOfFloat(0)));
//							negativeFeatureMat.push_back(featureVector.t());
						}
					}
				}
				
				log.info("Extracted FeatureVectors: " + result.size());
				return result;
			}
		};
	}
}
