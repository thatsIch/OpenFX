package de.thatsich.openfx.network.intern.control.prediction;

import de.thatsich.openfx.network.api.control.entity.INetwork;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class Network implements INetwork
{
	private final NetworkConfig config;

	public Network(NetworkConfig config)
	{

		this.config = config;
	}

	@Override
	public NetworkConfig getConfig()
	{
		return this.config;
	}
}
