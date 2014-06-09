package de.thatsich.openfx.classification.api.control.entity;

import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import org.opencv.core.MatOfFloat;

public interface IBinaryClassifier
{
	IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassificationConfig config);

	String getName();
}
