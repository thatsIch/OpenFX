package de.thatsich.openfx.classification.intern.control.classifier;

import com.google.inject.Inject;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classification.SVMTrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.ABinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvSVM;
import org.opencv.ml.CvSVMParams;


public class SVMBinaryClassifier extends ABinaryClassifier
{
	@Inject private IClassificationCommandProvider provider;

	@Override
	public ITrainedBinaryClassifier train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassificationConfig config)
	{
		final long startTime = System.currentTimeMillis();

		final CvSVM svm = new CvSVM();

		// Labels
		final Mat positiveLabels = Mat.ones(positiveTrainData.rows(), 1, CvType.CV_8U);
		final Mat negativeLabels = Mat.zeros(negativeTrainData.rows(), 1, CvType.CV_8U);

		Mat trainLabels = new MatOfFloat();
		trainLabels.push_back(positiveLabels);
		trainLabels.push_back(negativeLabels);
		trainLabels.convertTo(trainLabels, CvType.CV_32FC1);
		this.log.info("Labels with Size (" + trainLabels.cols() + ", " + trainLabels.rows() + ") is Type " + trainLabels.type());

		// Data
		MatOfFloat trainData = new MatOfFloat();
		trainData.push_back(positiveTrainData);
		trainData.push_back(negativeTrainData);
		trainData.convertTo(trainData, CvType.CV_32FC1);
		this.log.info("Data with Size (" + trainData.cols() + ", " + trainData.rows() + ") is Type " + trainData.type());

		svm.train(trainData, trainLabels);
		new CvSVMParams();

		final String className = config.classificationName.get();
		final String extractorName = config.extractorName.get();
		final int tileSize = config.tileSize.get();
		final String errorName = config.errorName.get();
		final String id = config.id.get();

		final long endTime = System.currentTimeMillis();
		final long learnTime = endTime - startTime;

		final BinaryClassificationConfig newConfig = new BinaryClassificationConfig(className, extractorName, tileSize, errorName, id, learnTime);
		final SVMTrainedBinaryClassifier bc = this.provider.createSVMTraindBinaryClassifier(svm, newConfig);

		return bc;
	}
}
