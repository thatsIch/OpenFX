package de.thatsich.openfx.network.api.model;

import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface INetworks
{
	ListProperty<ITrainedNetwork> list();

	ObjectProperty<ITrainedNetwork> selected();
}
