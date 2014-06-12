package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author thatsIch
 * @since 02.06.2014.
 */
public class ClassSelection
{
	public String predict(List<Pair<String, Double>> values)
	{
		final double sum = this.getSum(values);
		final List<Pair<String, Double>> normalized = this.normalize(values, sum);
		final List<Pair<String, Double>> sorted = this.sort(normalized);
		final String random = this.getRandomElement(sorted);

		return random;

	}

	private double getSum(List<Pair<String, Double>> values)
	{
		double sum = 0;
		for (Pair<String, Double> value : values)
		{
			sum += value.getValue();
		}

		return sum;
	}

	private List<Pair<String, Double>> normalize(List<Pair<String, Double>> values, double sum)
	{
		final List<Pair<String, Double>> normalized = new LinkedList<>();

		for (Pair<String, Double> pair : values)
		{
			final double modified = pair.getValue() / sum;
			final String key = pair.getKey();
			final Pair<String, Double> newPair = new Pair<>(key, modified);

			normalized.add(newPair);
		}

		return normalized;
	}

	private List<Pair<String, Double>> sort(List<Pair<String, Double>> normalized)
	{
		normalized.sort((p1, p2) -> p1.getValue().compareTo(p2.getValue()));

		return normalized;
	}

	private String getRandomElement(List<Pair<String, Double>> sorted)
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final double gaussian = random.nextGaussian() * sorted.size();
		final long rounded = Math.round(gaussian);
		final int index = (int) rounded;

		return sorted.get(index).getKey();
	}
}
