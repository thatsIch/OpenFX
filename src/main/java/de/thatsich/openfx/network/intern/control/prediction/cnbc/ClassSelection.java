package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import com.google.inject.Inject;
import de.thatsich.core.Log;
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
	private final Log log;

	@Inject
	public ClassSelection(Log log)
	{
		this.log = log;
	}

	public String predict(List<Pair<String, Double>> values)
	{
		final List<Pair<String, Double>> sorted = this.sort(values);
		final String random = this.getWinnerElement(sorted);

		return random;
	}

	private List<Pair<String, Double>> sort(List<Pair<String, Double>> toBeSorted)
	{
		toBeSorted.sort((p1, p2) -> p1.getValue().compareTo(p2.getValue()));
		this.log.info("Sorted pairs: " + toBeSorted.get(0) + " < " + toBeSorted.get(toBeSorted.size() - 1) + " in " + toBeSorted);

		return toBeSorted;
	}

	private String getWinnerElement(List<Pair<String, Double>> sorted)
	{
		final Pair<String, Double> winner;
		try
		{
			final int lastItemIndex = sorted.size();
			winner = sorted.get(lastItemIndex - 1);
			this.log.info("Select winner pair " + winner);

			return winner.getKey();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}
}
