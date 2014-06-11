package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import java.util.List;

/**
 * @author thatsIch
 * @since 02.06.2014.
 */
public class Fuser
{
	public void train(List<INBC> nbcs)
	{
		nbcs.sort((low, high) -> {
			return low;
		});
		for (INBC nbc : nbcs)
		{
			nbc.getFuserOutput();
		}
	}
}
