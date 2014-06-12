package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Fuser uses values to find a normal distributed random value
 * and picks this as a represententiv. Values will get
 * normalized and sorted before doing so
 *
 * @author thatsIch
 * @since 02.06.2014.
 */
public class Fuser
{
	/**
	 * fuser predicts a representiv value
	 *
	 * @param values potential representives
	 *
	 * @return representiv
	 */
	public double predict(List<Double> values)
	{
		final double sum = this.getSum(values);
		final List<Double> normalized = this.normalize(values, sum);
		final List<Double> sorted = this.sort(normalized);
		final double random = this.getRandomElement(sorted);

		return random;
	}

	/**
	 * Calculates the sum of values
	 *
	 * @param values sum of this
	 *
	 * @return sum
	 */
	private double getSum(List<Double> values)
	{
		double sum = 0;
		for (Double value : values)
		{
			sum += value;
		}

		return sum;
	}

	/**
	 * Normalize a list by a value, prefered sum
	 *
	 * @param values to be normalized
	 * @param sum    by this
	 *
	 * @return normalized values
	 */
	private List<Double> normalize(List<Double> values, double sum)
	{
		final List<Double> normalized = new LinkedList<>();

		for (Double value : values)
		{
			final double modified = value / sum;
			normalized.add(modified);
		}

		return normalized;
	}

	/**
	 * Sorts the list
	 *
	 * @param normalized to be sorted
	 *
	 * @return sorted list
	 */
	private List<Double> sort(List<Double> normalized)
	{
		Collections.sort(normalized);

		return normalized;
	}

	/**
	 * uses gaussian distribution to pick a random value
	 *
	 * @param sorted sorted list
	 *
	 * @return random value from sorted list
	 */
	private double getRandomElement(List<Double> sorted)
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final double gaussian = random.nextGaussian() * sorted.size();
		final long rounded = Math.round(gaussian);
		final int index = (int) rounded;

		return sorted.get(index);
	}
}
