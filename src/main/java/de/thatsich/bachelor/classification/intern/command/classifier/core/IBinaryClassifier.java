package de.thatsich.bachelor.classification.intern.command.classifier.core;

import org.opencv.core.MatOfFloat;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;

public interface IBinaryClassifier {
	IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config);
	String getName();
}
