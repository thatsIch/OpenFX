package de.thatsich.openfx.network.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.network.intern.control.command.commands.GetLastNetworkIndexCommand;
import de.thatsich.openfx.network.intern.control.command.commands.InitNetworksCommand;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface INetworkInitCommandProvider extends ICommandProvider
{
	InitNetworksCommand createInitNetworksCommand();

	GetLastNetworkIndexCommand createGetLastNetworkIndexCommand();
}
