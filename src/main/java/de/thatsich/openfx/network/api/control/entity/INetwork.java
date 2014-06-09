package de.thatsich.openfx.network.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.network.intern.control.entity.NetworkConfig;

/**
 * @author thatsIch
 * @since 08.06.2014.
 */
public interface INetwork extends IEntity
{
	@Override
	NetworkConfig getConfig();
}
