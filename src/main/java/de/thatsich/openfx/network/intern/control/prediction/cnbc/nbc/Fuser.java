package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import java.util.List;

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
		if (values.size() > 0)
		{
			return values.stream().reduce(Math::max).get();
		}
		else
		{
			return 0;
		}
	}
}
