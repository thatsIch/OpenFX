package de.thatsich.bachelor.classificationtraining.api.entities;

import org.opencv.core.MatOfFloat;

import de.thatsich.bachelor.classificationtraining.restricted.model.logic.BinaryClassifierConfiguration;

public interface IBinaryClassifier {
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config);
	public String getName();
}
