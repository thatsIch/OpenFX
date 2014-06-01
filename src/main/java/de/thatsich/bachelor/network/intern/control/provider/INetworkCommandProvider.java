package de.thatsich.bachelor.network.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.network.api.entities.Network;
import de.thatsich.bachelor.network.intern.control.command.commands.DeleteNetworkCommand;
import de.thatsich.bachelor.network.intern.control.command.commands.SetLastNetworkIndexCommand;
import de.thatsich.bachelor.network.intern.control.command.commands.TrainNetworkCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface INetworkCommandProvider extends ICommandProvider
{
	TrainNetworkCommand createTrainNetworkCommand(@Assisted Path path);

	DeleteNetworkCommand createDeleteNetworkCommand(Network selected);

	SetLastNetworkIndexCommand createSetLastNetworkIndexCommand(@Assisted int index);
}
