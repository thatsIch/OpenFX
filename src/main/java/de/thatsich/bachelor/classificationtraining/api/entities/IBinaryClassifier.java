package de.thatsich.bachelor.classificationtraining.api.entities;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

public interface IBinaryClassifier {
	public void train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData);
	public double predict(Mat sample);
	public String getName();
}
