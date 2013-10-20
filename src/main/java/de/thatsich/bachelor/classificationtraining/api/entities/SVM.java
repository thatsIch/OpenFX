package de.thatsich.bachelor.classificationtraining.api.entities;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvSVM;

import de.thatsich.core.opencv.ABinaryClassifier;

public class SVM extends ABinaryClassifier {

	private final CvSVM svm = new CvSVM();
	private boolean isTrained = false;

	@Override
	public void train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData) {

		// Labels
		final Mat positiveLabels = Mat.ones(positiveTrainData.rows(), 1, CvType.CV_8U);
		final Mat negativeLabels = Mat.zeros(negativeTrainData.rows(), 1, CvType.CV_8U);
		
		Mat trainLabels = new MatOfFloat();
		trainLabels.push_back(positiveLabels);
		trainLabels.push_back(negativeLabels);
		trainLabels.convertTo(trainLabels, CvType.CV_32FC1);
		this.log.info("Labels with Size (" + trainLabels.cols() + ", " + trainLabels.rows() + ") is " + trainLabels.type());
		
		// Data
		MatOfFloat trainData = new MatOfFloat();
		trainData.push_back(positiveTrainData);
		trainData.push_back(negativeTrainData);
		trainData.convertTo(trainData, CvType.CV_32FC1);
		this.log.info("Data with Size (" + trainData.cols() + ", " + trainData.rows() + ") is " + trainData.type());
		
		this.svm.train(trainData, trainLabels);
		
		this.isTrained = true;
	}

	@Override
	public double predict(Mat sample) {
		if (this.isTrained == false) throw new IllegalStateException("SVM not trained.");
		
		return this.svm.predict(sample);
	}
}
