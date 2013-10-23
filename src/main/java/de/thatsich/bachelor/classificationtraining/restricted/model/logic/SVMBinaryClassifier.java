package de.thatsich.bachelor.classificationtraining.restricted.model.logic;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvSVM;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;

public class SVMBinaryClassifier extends ABinaryClassifier {

	@Override
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData) {
		final CvSVM svm = new CvSVM();
		
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
		
		svm.train(trainData, trainLabels);

		return null;
	}
}
