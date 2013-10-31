package de.thatsich.bachelor.prediction.intern.command.evaluation;

/**
 * Contains the result of how many
 * - true positive
 * - false positive
 * - true negative
 * - false negative
 * 
 * a prediction had. Can evaluate them
 * with simple precision and recall functions
 * 
 * Usage:
 * pr = new PrecisionRecall(int truePositive, int falsePositive, int trueNegative, int falseNegative);
 * pr.precision();
 * pr.recall();
 * pr.specificity();
 * pr.accuracy();
 * 
 * @author Minh
 *
 */
public class PrecisionRecall {
	/**
	 * PPV (positive predictive value)
	 * Precision is the probability that a (randomly selected) retrieved document is relevant.
	 * 
	 * @param truePositive Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative Correct Absence of Result
	 * @param falseNegative Missing Result
	 * 
	 * @return precision
	 */
	public double precision(int truePositive, int falsePositive, int trueNegative, int falseNegative) { 
		return 
				truePositive / 
				(truePositive + falsePositive);
	}
	
	/**
	 * NPV (negative predictive value)
	 * Recall is the probability that a (randomly selected) relevant document is retrieved in a search.
	 * 
	 * @param truePositive Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative Correct Absence of Result
	 * @param falseNegative Missing Result
	 * 
	 * @return recall
	 */
	public double recall(int truePositive, int falsePositive, int trueNegative, int falseNegative) { 
		return 
				truePositive / 
				(truePositive + falseNegative); 
	}
	
	/**
	 * SPC (Specificity or TrueNegativeRate)
	 * Specificity relates to the test's ability to identify negative results.
	 * 
	 * @param truePositive Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative Correct Absence of Result
	 * @param falseNegative Missing Result
	 * 
	 * @return specificity
	 */
	public double specificity(int truePositive, int falsePositive, int trueNegative, int falseNegative) { 
		return 
				trueNegative / 
				(trueNegative + falsePositive); 
	}
	
	/**
	 * ACC (Accuracy)
	 * An accuracy of 100% means that the measured values are exactly the same as the given values.
	 * 
	 * @param truePositive Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative Correct Absence of Result
	 * @param falseNegative Missing Result
	 * 
	 * @return accuracy
	 */
	public double accuracy(int truePositive, int falsePositive, int trueNegative, int falseNegative) { 
		return 
				(truePositive + trueNegative) / 
				(truePositive + trueNegative + falsePositive + falseNegative);
	}
}
