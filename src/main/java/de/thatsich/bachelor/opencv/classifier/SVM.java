package de.thatsich.bachelor.opencv.classifier;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvSVM;

import de.thatsich.core.opencv.classifier.ABinaryClassifier;

public class SVM extends ABinaryClassifier {

	final CvSVM svm;
	boolean isTrained;
	
	public SVM() {
		this.svm = new CvSVM();
		this.isTrained = false;
	}
	
	@Override
	public void train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData) {
		final Mat trainImages = new Mat();
		final Mat trainLabels = new Mat();
		
		// positiv matches
		trainImages.push_back(positiveTrainData);
		final Mat positivLabels = Mat.ones(positiveTrainData.rows(), 1, CvType.CV_8U);
		trainLabels.push_back(positivLabels);
		
		// negativ matches
		trainImages.push_back(negativeTrainData);
		final Mat negativLabels = Mat.zeros(negativeTrainData.rows(), 1, CvType.CV_8U);
		trainLabels.push_back(negativLabels);
		
		this.svm.train(trainImages, trainLabels);
		
		this.isTrained = true;
	}

	@Override
	public double predict(Mat sample) {
		if (this.isTrained == false) throw new IllegalStateException("SVM not trained.");
		
		return this.svm.predict(sample);
	}
}
