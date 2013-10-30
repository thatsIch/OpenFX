package de.thatsich.bachelor.classification.intern.command.classifier;

import org.opencv.core.MatOfFloat;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;

public interface IBinaryClassifier {
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config);
	public String getName();
}
