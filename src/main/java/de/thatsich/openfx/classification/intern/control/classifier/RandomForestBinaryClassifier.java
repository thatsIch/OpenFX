package de.thatsich.openfx.classification.intern.control.classifier;

import com.google.inject.Inject;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classification.RandomForestTraindBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.ABinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.CvRTParams;
import org.opencv.ml.CvRTrees;


public class RandomForestBinaryClassifier extends ABinaryClassifier
{
	@Inject IClassificationCommandProvider provider;

	@Override
	public ITrainedBinaryClassifier train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassificationConfig config)
	{
		final long startTime = System.currentTimeMillis();

		// Labels
		final Mat positiveLabels = Mat.ones(positiveTrainData.rows(), 1, CvType.CV_8U);
		final Mat negativeLabels = Mat.zeros(negativeTrainData.rows(), 1, CvType.CV_8U);

		final int sampleCount = (int) Math.ceil(0.01 * (positiveTrainData.rows() + negativeTrainData.rows()));
		final CvRTrees trees = new CvRTrees();
		final CvRTParams params = new CvRTParams();
		params.set_max_depth(25);
		params.set_min_sample_count(sampleCount);
		params.set_term_crit(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 100, 0F));


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

		trees.train(trainData, 1, trainLabels, new Mat(), new Mat(), new Mat(), new Mat(), params);

		final String className = config.classificationName.get();
		final String extractorName = config.extractorName.get();
		final int tileSize = config.tileSize.get();
		final String errorName = config.errorName.getName();
		final String id = config.id.getName();

		final long endTime = System.currentTimeMillis();
		final long learnTime = endTime - startTime;

		final BinaryClassificationConfig newConfig = new BinaryClassificationConfig(className, extractorName, tileSize, errorName, id, learnTime);
		final RandomForestTraindBinaryClassifier bc = this.provider.createRandomForestTraindBinaryClassifier(trees, newConfig);

		return bc;
	}
}
