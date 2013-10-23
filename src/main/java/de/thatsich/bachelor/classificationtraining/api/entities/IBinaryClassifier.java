package de.thatsich.bachelor.classificationtraining.api.entities;

import org.opencv.core.MatOfFloat;

public interface IBinaryClassifier {
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData);
	public String getName();
}
