package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.api.entities.TrainedBinaryClassifier;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.Command;

public class TrainBinaryClassifierCommand extends Command<TrainedBinaryClassifier> {

	// Properties
	private final ObjectProperty<IBinaryClassifier> binaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	private final ObjectProperty<FeatureVectorSet> selectedFeatureVector = new SimpleObjectProperty<FeatureVectorSet>();
	private final ListProperty<FeatureVectorSet> featureVectorList = new SimpleListProperty<FeatureVectorSet>();
	
	@Inject
	public TrainBinaryClassifierCommand(@Assisted IBinaryClassifier classifier, @Assisted FeatureVectorSet selected, @Assisted List<FeatureVectorSet> all) {
		this.binaryClassifier.set(classifier);
		this.selectedFeatureVector.set(selected);
		this.featureVectorList.setAll(all);
	}
	
	@Override
	protected Task<TrainedBinaryClassifier> createTask() {
		return new Task<TrainedBinaryClassifier>() {
			@Override protected TrainedBinaryClassifier call() throws Exception {
				return new TrainedBinaryClassifier();
			}
		};
	}

}
