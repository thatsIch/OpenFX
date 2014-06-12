package de.thatsich.openfx.network.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.network.intern.control.prediction.NetworkConfig;

/**
 * @author thatsIch
 * @since 08.06.2014.
 */
public interface ITrainedNetwork extends IEntity
{
	long getTrainTime();

	String predict(IError error) throws Exception;

	@Override
	NetworkConfig getConfig();
}
