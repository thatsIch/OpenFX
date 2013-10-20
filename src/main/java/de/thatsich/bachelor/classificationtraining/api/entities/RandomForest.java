package de.thatsich.bachelor.classificationtraining.api.entities;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.CvRTParams;
import org.opencv.ml.CvRTrees;

public class RandomForest extends ABinaryClassifier {

	private final CvRTrees trees;
	private final CvRTParams params;
	private boolean isTrained;
	
	/**
	 * 
	 * @param log
	 */
	public RandomForest() {
		this.trees = new CvRTrees();
		
		this.params = new CvRTParams();
		this.params.set_max_depth(25);
		this.params.set_min_sample_count(5);
		this.params.set_regression_accuracy(0);
		this.params.set_use_surrogates(false);
		this.params.set_max_categories(25);
		this.params.set_calc_var_importance(false);
		this.params.set_nactive_vars(4);
		this.params.set_term_crit(
			new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 100, 0.0F)
		);
		
		this.isTrained = false;
	}
	
	@Override
	public double predict(Mat sample) {
		if (this.isTrained == false) throw new IllegalStateException("Random Forest not trained.");
		
		return this.trees.predict_prob(sample);
	}

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
		
//		Mat varType = Mat.zeros(trainData.cols(), 1, CvType.CV_8UC1);
		this.trees.train(trainData, 1, trainLabels, new Mat(), new Mat(), new Mat(), new Mat(), this.params);
		this.isTrained = true;
	}

}
