package de.thatsich.openfx.classification.intern.control.classifier.core;

import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import org.opencv.core.MatOfFloat;

public interface IBinaryClassifier
{
	IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config);

	String getName();
}
