package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import java.util.Collections;
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
		final List<Double> sorted = this.sort(values);
		final double random = this.getWinnerElement(sorted);

		return random;
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
	private double getWinnerElement(List<Double> sorted)
	{
		if (sorted.size() > 0)
		{
			final int bound = sorted.size();
			final int randomIndex = this.getNextNormalDistributedRandomBetweenZeroAndX(bound);
			final double winner = sorted.get(randomIndex);

			return winner;
		}
		else
		{
			return 0;
		}
	}

	private int getNextNormalDistributedRandomBetweenZeroAndX(double x)
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();

		int result;
		do
		{
			final double fit = random.nextGaussian() / 3 * x + 0.5;
			result = (int) Math.round(fit);
		} while (result < 0 || result > 1);

		return result;
	}
}
