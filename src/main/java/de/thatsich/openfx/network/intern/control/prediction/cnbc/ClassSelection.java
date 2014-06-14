package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import javafx.util.Pair;

import java.util.List;

/**
 * Class Selection with Winner takes it all
 *
 * @author thatsIch
 * @since 02.06.2014.
 */
public class ClassSelection
{
	public String predict(List<Pair<String, Double>> values)
	{
		final List<Pair<String, Double>> sorted = this.sort(values);
		final String random = this.getWinnerElement(sorted);

		return random;

	}

	private List<Pair<String, Double>> sort(List<Pair<String, Double>> toBeSorted)
	{
		toBeSorted.sort((p1, p2) -> p1.getValue().compareTo(p2.getValue()));

		return toBeSorted;
	}

	private String getWinnerElement(List<Pair<String, Double>> sorted)
	{
		final int lastItemIndex = sorted.size();
		final Pair<String, Double> winner = sorted.get(lastItemIndex);

		return winner.getKey();
	}
}
