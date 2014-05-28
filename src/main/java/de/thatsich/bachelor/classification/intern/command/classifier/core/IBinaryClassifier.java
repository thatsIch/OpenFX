package de.thatsich.bachelor.classification.intern.command.classifier.core;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import org.opencv.core.MatOfFloat;

public interface IBinaryClassifier
{
	IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config);

	String getName();
}
