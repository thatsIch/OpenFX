package de.thatsich.bachelor.opencv.classifier;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.CvRTrees;

import de.thatsich.core.opencv.classifier.ABinaryClassifier;

public class RandomForest extends ABinaryClassifier {

	private final CvRTrees trees;
	private boolean isTrained;
	
	public RandomForest() {
		this.trees = new CvRTrees();
		this.isTrained = false;
	}
	
	@Override
	public double predict(Mat sample) {
		if (this.isTrained == false) throw new IllegalStateException("Random Forest not trained.");
		
		return this.trees.predict(sample);
	}

	@Override
	public void train(Mat positiveTrainData, Mat negativeTrainData) {
		final Mat trainData = new Mat();
		final Mat trainLabels = new Mat();
		final Mat positiveLabels = Mat.ones(positiveTrainData.rows(), 1, CvType.CV_8U);
		final Mat negativeLabels = Mat.zeros(negativeTrainData.rows(), 1, CvType.CV_8U);
		
		trainData.push_back(positiveTrainData);
		trainLabels.push_back(positiveLabels);
		
		trainData.push_back(negativeTrainData);
		trainLabels.push_back(negativeLabels);
		
		this.trees.train(trainData, 1, negativeLabels);
	}

}
