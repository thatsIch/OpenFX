package de.thatsich.bachelor.javafx.business.command;

import java.util.UUID;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IFeatureExtractor;

public class ExtractFeatureVectorCommand extends Command<FeatureVector> {

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
	protected Task<FeatureVector> createTask() {
		return new Task<FeatureVector>() {

			// TODO Label richtig berechnen
			// TODO über ganzes Bild iteratieren
			// TODO wahrscheinlich Randfälle weglassen, weil nicht gut
			
			@Override
			protected FeatureVector call() throws Exception {
				final String className = errorEntry.get().getErrorClassProperty().get();
				final String extractorName = featureExtractor.get().getName();
				final int size = frameSize.get();
				final String id = UUID.randomUUID().toString();
				final MatOfFloat featureLabel = new MatOfFloat(1);
				final MatOfFloat featureVectorMat = featureExtractor.get().extractFeature(errorEntry.get().getOriginalWithErrorMat());

				return new FeatureVector(className, extractorName, size, id, featureVectorMat, featureLabel);
			}
		};
	}
}
