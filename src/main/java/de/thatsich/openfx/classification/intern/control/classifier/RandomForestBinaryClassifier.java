package de.thatsich.openfx.classification.intern.control.classifier;

import com.google.inject.Inject;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.ABinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.openfx.classification.intern.control.provider.IBinaryClassificationProvider;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.CvRTParams;
import org.opencv.ml.CvRTrees;


public class RandomForestBinaryClassifier extends ABinaryClassifier
{
	@Inject
	private IBinaryClassificationProvider provider;

	@Override
	public IBinaryClassification train(MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config)
	{
		final CvRTrees trees = new CvRTrees();
		final CvRTParams params = new CvRTParams();
		params.set_max_depth(25);
		params.set_min_sample_count(5);
		params.set_regression_accuracy(0);
		params.set_use_surrogates(false);
		params.set_max_categories(25);
		params.set_calc_var_importance(false);
		params.set_nactive_vars(4);
		params.set_term_crit(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 100, 0.0F));

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

		trees.train(trainData, 1, trainLabels, new Mat(), new Mat(), new Mat(), new Mat(), params);

		return this.provider.createRandomForestBinaryClassification(trees, config);
	}
}
