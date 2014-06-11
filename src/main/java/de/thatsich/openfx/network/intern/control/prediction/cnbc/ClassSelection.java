package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
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
	public void train(List<INBC> nbcs)
	{
		List<Pair<String, Double>> values = new LinkedList<>();
		nbcs.sort((nbc1, nbc2) -> {
			final Double output1 = nbc1.getFuserOutput();
			final Double output2 = nbc2.getFuserOutput();

			return output1.compareTo(output2);
		});

		double sum = 0;
		for (INBC nbc : nbcs)
		{
			sum += nbc.getFuserOutput();
		}

		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final double weight = random.nextDouble();


	}

	public String predict(List<Pair<String, Double>> predict)
	{
		return null;
	}
}
