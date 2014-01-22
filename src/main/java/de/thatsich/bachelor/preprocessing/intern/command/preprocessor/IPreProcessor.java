package de.thatsich.bachelor.preprocessing.intern.command.preprocessor;

import org.opencv.core.MatOfFloat;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;

public interface IPreProcessor {
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, PreProcesserConfiguration config);
	public String getName();
}
