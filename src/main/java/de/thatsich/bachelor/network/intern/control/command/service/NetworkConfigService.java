package de.thatsich.bachelor.network.intern.control.command.service;

import de.thatsich.core.AConfigurationService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkConfigService extends AConfigurationService
{
	private static final String LAST_NETWORK_INDEX = "last_network_index";
	private static final int DEFAULT_LAST_NETWORK_INDEX = 0;

	public Integer getLastNetworkIndex()
	{
		return super.get(LAST_NETWORK_INDEX, DEFAULT_LAST_NETWORK_INDEX);
	}
	public void setLastNetworkIndex(int index) {
		super.set(LAST_NETWORK_INDEX, index);
	}
}
