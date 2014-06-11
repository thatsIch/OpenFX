package de.thatsich.openfx.network.intern.control.prediction;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;

/**
 * @author thatsIch
 * @since 10.06.2014.
 */
public interface INetworkSpace
{
	void train() throws Exception;

	String predict(IError image) throws Exception;
}
