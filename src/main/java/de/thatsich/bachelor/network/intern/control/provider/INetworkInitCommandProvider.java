package de.thatsich.bachelor.network.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.network.intern.control.command.commands.GetLastNetworkIndexCommand;
import de.thatsich.bachelor.network.intern.control.command.commands.InitNetworkFolderCommand;
import de.thatsich.bachelor.network.intern.control.command.commands.InitNetworkListCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface INetworkInitCommandProvider extends ICommandProvider
{
	InitNetworkFolderCommand createInitNetworkFolderCommand(@Assisted Path networkInputPath);

	InitNetworkListCommand createInitNetworkListCommand(@Assisted Path networkInputPath);

	GetLastNetworkIndexCommand createGetLastNetworkIndexCommand();
}
