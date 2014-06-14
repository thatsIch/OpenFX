package de.thatsich.openfx.network.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.network.intern.control.prediction.NetworkConfig;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;

/**
 * @author thatsIch
 * @since 08.06.2014.
 */
public interface ITrainedNetwork extends IEntity
{
	ICNBC getCnbc();

	String predict(IError error) throws Exception;

	ReadOnlyStringProperty date();

	ReadOnlyStringProperty id();

	ReadOnlyLongProperty trainTime();

	@Override
	NetworkConfig getConfig();


}
