package de.thatsich.openfx.prediction.intern.control.evaluation;

/**
 * Contains the result of how many - true positive - false positive - true negative - false negative
 *
 * a prediction had. Can evaluate them with simple precision and recall functions
 *
 * Usage: pr = new PrecisionRecall(int truePositive, int falsePositive, int trueNegative, int falseNegative);
 * pr.precision(); pr.recall(); pr.specificity(); pr.accuracy();
 *
 * @author Minh
 */
public class PrecisionRecall
{

	/**
	 * PPV (positive predictive value) Precision is the probability that a (randomly selected) retrieved document is
	 * relevant.
	 *
	 * @param truePositive  Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative  Correct Absence of Result
	 * @param falseNegative Missing Result
	 *
	 * @return precision
	 */
	public double precision(long truePositive, long falsePositive, long trueNegative, long falseNegative)
	{
		if (truePositive + falsePositive == 0)
		{
			return 0;
		}
		else
		{
			return 1.0 * truePositive / (truePositive + falsePositive);
		}
	}

	/**
	 * NPV (negative predictive value) Recall is the probability that a (randomly selected) relevant document is
	 * retrieved in a search.
	 *
	 * @param truePositive  Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative  Correct Absence of Result
	 * @param falseNegative Missing Result
	 *
	 * @return recall
	 */
	public double recall(long truePositive, long falsePositive, long trueNegative, long falseNegative)
	{
		if (truePositive + falseNegative == 0)
		{
			return 0;
		}
		else
		{
			return 1.0 * truePositive / (truePositive + falseNegative);
		}
	}

	/**
	 * SPC (Specificity or TrueNegativeRate) Specificity relates to the test's ability to identify negative results.
	 *
	 * @param truePositive  Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative  Correct Absence of Result
	 * @param falseNegative Missing Result
	 *
	 * @return specificity
	 */
	public double specificity(long truePositive, long falsePositive, long trueNegative, long falseNegative)
	{
		if (trueNegative + falsePositive == 0)
		{
			return 0;
		}
		else
		{
			return 1.0 * trueNegative / (trueNegative + falsePositive);
		}
	}

	/**
	 * ACC (Accuracy) An accuracy of 100% means that the measured values are exactly the same as the given values.
	 *
	 * @param truePositive  Correct Result
	 * @param falsePositive Unexpected Result
	 * @param trueNegative  Correct Absence of Result
	 * @param falseNegative Missing Result
	 *
	 * @return accuracy
	 */
	public double accuracy(long truePositive, long falsePositive, long trueNegative, long falseNegative)
	{
		if (truePositive + trueNegative + falsePositive + falseNegative == 0)
		{
			return 0;
		}
		else
		{
			return 1.0 * (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
		}
	}

	/**
	 * F1 Measure where recall and precision are evenly weighted.
	 *
	 * @param precision Precision
	 * @param recall    Recall
	 *
	 * @return F1 Measure
	 */
	public double f1(double precision, double recall)
	{
		return this.f(precision, recall, 1);
	}

	/**
	 * F Measure harmonic mean of precision and recall
	 *
	 * @param precision Precision
	 * @param recall    Recall
	 * @param beta      Weight
	 *
	 * @return F Measure
	 */
	public double f(double precision, double recall, double beta)
	{
		final double betasquare = beta * beta;
		if (betasquare * precision + recall == 0)
		{
			return 0;
		}
		else
		{
			return (1 + betasquare) * precision * recall / (betasquare * precision + recall);
		}
	}

	/**
	 * F0.5 Measure which puts more emphasis on precision than recall
	 *
	 * @param precision Precision
	 * @param recall    Recall
	 *
	 * @return F0.5 Measure
	 */
	public double f05(double precision, double recall)
	{
		return this.f(precision, recall, 0.5);
	}

	/**
	 * F2 Measure which puts more emphasis on recall than precision
	 *
	 * @param precision Precision
	 * @param recall    Recall
	 *
	 * @return F2 Measure
	 */
	public double f2(double precision, double recall)
	{
		return this.f(precision, recall, 2);
	}
}
