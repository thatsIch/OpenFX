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
	 * Correct Result
	 */
	private final int truePositive;
	
	/**
	 * Unexpected Result
	 */
	private final int falsePositive;
	
	/**
	 * Correct Absence of Result
	 */
	private final int trueNegative;
	
	/**
	 * Missing Result
	 */
	private final int falseNegative;
	
	/**
	 * CTOR
	 * 
	 * @param truePositive Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative Correct Absence of Result
	 * @param falseNegative Missing Result
	 */
	public PrecisionRecall(
			int truePositive, int falsePositive,
			int trueNegative, int falseNegative) {
		this.truePositive = truePositive;
		this.falsePositive = falsePositive;
		this.trueNegative = trueNegative;
		this.falseNegative = falseNegative;
	}
	
	/**
	 * PPV (positive predictive value)
	 * Precision is the probability that a (randomly selected) retrieved document is relevant.
	 * @return precision
	 */
	public double precision() { 
		return 
				this.truePositive / 
				(this.truePositive + this.falsePositive);
	}
	
	/**
	 * NPV (negative predictive value)
	 * Recall is the probability that a (randomly selected) relevant document is retrieved in a search.
	 * @return recall
	 */
	public double recall() { 
		return 
				this.truePositive / 
				(this.truePositive + this.falseNegative); 
	}
	
	/**
	 * SPC (Specificity or TrueNegativeRate)
	 * Specificity relates to the test's ability to identify negative results.
	 * @return specificity
	 */
	public double specificity() { 
		return 
				this.trueNegative / 
				(this.trueNegative + this.falsePositive); 
	}
	
	/**
	 * ACC (Accuracy)
	 * An accuracy of 100% means that the measured values are exactly the same as the given values.
	 * @return accuracy
	 */
	public double accuracy() { 
		return 
				(this.truePositive + this.trueNegative) / 
				(this.truePositive + this.trueNegative + this.falsePositive + this.falseNegative);
	}
}
