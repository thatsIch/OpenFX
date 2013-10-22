package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;

import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.api.entities.TrainedBinaryClassifier;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.Command;

public class TrainBinaryClassifierCommand extends Command<TrainedBinaryClassifier> {

	// Properties
	private final ObjectProperty<IBinaryClassifier> binaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	private final ObjectProperty<FeatureVectorSet> selectedFeatureVector = new SimpleObjectProperty<FeatureVectorSet>();
	private final ListProperty<FeatureVectorSet> featureVectorList = new SimpleListProperty<FeatureVectorSet>(FXCollections.<FeatureVectorSet>observableArrayList());
	
	@Inject
	public TrainBinaryClassifierCommand(@Assisted IBinaryClassifier classifier, @Assisted FeatureVectorSet selected, @Assisted List<FeatureVectorSet> all) {
		this.binaryClassifier.set(classifier);
		this.selectedFeatureVector.set(selected);
		this.featureVectorList.addAll(all);
	}
	
	@Override
	protected Task<TrainedBinaryClassifier> createTask() {
		return new Task<TrainedBinaryClassifier>() {
			@Override protected TrainedBinaryClassifier call() throws Exception {
				final IBinaryClassifier bc = binaryClassifier.get();
				final FeatureVectorSet selected = selectedFeatureVector.get();
				final List<FeatureVectorSet> list = featureVectorList.get();
				
				final MatOfFloat positive = new MatOfFloat();
				final MatOfFloat negative = new MatOfFloat();
				log.info("Prepared all data for Training.");
				
				// run through all FeatureVectorSets matching same categories (same FrameSize, same Extractor, same ErrorClass)
				// which is not the selected one and their data to train
				// extract all float lists and transform them into MatOfFloats
				// use .t() on them to transpose them 
				for (FeatureVectorSet current : list) {
					if (!current.equals(selected)) {
						for (FeatureVector vector : current.getFeatureVectorList()) {
							
							final float[] floatArray = new float[vector.getVectorProperty().size()];
							int index = 0;
							for (Float f : vector.getVectorProperty()) {
								floatArray[index] = f;
								index++;
							}
							
							if (vector.getIsPositiveProperty().get()) {
								positive.push_back(new MatOfFloat(floatArray).t());
							}
							else {
								negative.push_back(new MatOfFloat(floatArray).t());
							}
						}
					}
				}
				log.info("Prepared Negative and Positive DataSets.");
				
				try {
					bc.train(positive, negative);	
					log.info("Trained Binary Classifier.");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				return new TrainedBinaryClassifier();
			}
		};
	}

}
