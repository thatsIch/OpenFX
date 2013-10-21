package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.api.entities.BinaryClassifier;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.Command;

public class TrainBinaryClassifierCommand extends Command<BinaryClassifier> {

	// Properties
	private final ObjectProperty<IBinaryClassifier> binaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	private final ObjectProperty<FeatureVectorSet> selectedFeatureVector = new SimpleObjectProperty<FeatureVectorSet>();
	private final ListProperty<FeatureVectorSet> featureVectorList = new SimpleListProperty<FeatureVectorSet>();
	
	@Inject
	public TrainBinaryClassifierCommand(@Assisted IBinaryClassifier classifier, @Assisted FeatureVectorSet selected, @Assisted List<FeatureVectorSet> all) {
		this.binaryClassifier.set(classifier);
		this.selectedFeatureVector.set(selected);
		this.featureVectorList.set(featureVectorList);
	}
	
	@Override
	protected Task<BinaryClassifier> createTask() {
		return new Task<BinaryClassifier>() {
			@Override protected BinaryClassifier call() throws Exception {
				return null;
			}
		};
	}

}
