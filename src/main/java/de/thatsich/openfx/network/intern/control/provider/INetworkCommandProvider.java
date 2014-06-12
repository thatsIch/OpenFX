package de.thatsich.openfx.network.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.command.commands.DeleteNetworkCommand;
import de.thatsich.openfx.network.intern.control.command.commands.SetLastNetworkIndexCommand;
import de.thatsich.openfx.network.intern.control.command.commands.TrainNetworkCommand;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface INetworkCommandProvider extends ICommandProvider
{
	TrainNetworkCommand createTrainNetworkCommand();

	DeleteNetworkCommand createDeleteNetworkCommand(ITrainedNetwork selected);

	SetLastNetworkIndexCommand createSetLastNetworkIndexCommand(@Assisted int index);
}
