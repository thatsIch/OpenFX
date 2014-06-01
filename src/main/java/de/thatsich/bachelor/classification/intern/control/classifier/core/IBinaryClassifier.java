package de.thatsich.bachelor.classification.intern.control.classifier.core;

import de.thatsich.bachelor.classification.api.model.IBinaryClassification;
import org.opencv.core.MatOfFloat;

public interface IBinaryClassifier
{
	IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config);

	String getName();
}
