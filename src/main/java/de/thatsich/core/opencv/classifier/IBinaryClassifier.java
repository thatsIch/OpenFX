package de.thatsich.core.opencv.classifier;

import org.opencv.core.Mat;

public interface IBinaryClassifier {
	public void train(Mat positiveTrainData, Mat negativeTrainData);
	public double predict(Mat sample);
	public String getName();
}
