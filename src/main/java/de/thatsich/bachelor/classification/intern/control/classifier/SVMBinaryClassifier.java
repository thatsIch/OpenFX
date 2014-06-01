package de.thatsich.bachelor.classification.intern.control.classifier;

import com.google.inject.Inject;
import de.thatsich.bachelor.classification.api.model.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.control.classifier.core.ABinaryClassifier;
import de.thatsich.bachelor.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.bachelor.classification.intern.control.provider.IBinaryClassificationProvider;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvSVM;


public class SVMBinaryClassifier extends ABinaryClassifier
{
	@Inject
	private IBinaryClassificationProvider provider;

	@Override
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config)
	{
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

		return this.provider.createSVMBinaryClassification(svm, config);
	}
}
