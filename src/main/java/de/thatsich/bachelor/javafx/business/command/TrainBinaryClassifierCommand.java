package de.thatsich.bachelor.javafx.business.command;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.BinaryClassifier;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IBinaryClassifier;

public class TrainBinaryClassifierCommand extends Command<BinaryClassifier> {

	// Properties
	private final ObjectProperty<IBinaryClassifier> binaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<FeatureVector>();
	private final ListProperty<FeatureVector> featureVectorList = new SimpleListProperty<FeatureVector>();
	
	@Inject
	public TrainBinaryClassifierCommand(@Assisted IBinaryClassifier classifier, @Assisted FeatureVector selected, @Assisted List<FeatureVector> all) {
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
