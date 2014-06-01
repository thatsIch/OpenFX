package de.thatsich.bachelor.network.intern.control.command;

import com.google.inject.Inject;
import de.thatsich.bachelor.network.api.model.INetworkState;
import de.thatsich.bachelor.network.intern.control.command.commands.GetLastNetworkIndexCommand;
import de.thatsich.bachelor.network.intern.control.command.commands.InitNetworkFolderCommand;
import de.thatsich.bachelor.network.intern.control.command.commands.InitNetworkListCommand;
import de.thatsich.bachelor.network.intern.control.handler.GetLastNetworkIndexSucceededHandler;
import de.thatsich.bachelor.network.intern.control.handler.InitNetworkListSucceededHandler;
import de.thatsich.bachelor.network.intern.control.provider.INetworkInitCommandProvider;
import de.thatsich.core.javafx.AInitCommander;
import de.thatsich.core.javafx.CommandExecutor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkInitCommander extends AInitCommander
{
	// Model
	@Inject private INetworkState state;

	// Command
	@Inject private INetworkInitCommandProvider provider;

	@Inject
	private void initNetworkList()
	{
		final Path networkInputPath = Paths.get("op/networks");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		this.log.info("Prepared Network Preparations.");

		this.state.setNetworkPath(networkInputPath);
		this.log.info("Set Network Path in Model.");

		final InitNetworkFolderCommand command = this.provider.createInitNetworkFolderCommand(networkInputPath);
		command.setExecutor(executor);
		command.start();
		this.log.info("Start NetworkFolder Creation.");

		final InitNetworkListCommand initCommand = this.provider.createInitNetworkListCommand(networkInputPath);
		initCommand.setOnSucceededCommandHandler(InitNetworkListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized NetworkList Retrieval.");

		final GetLastNetworkIndexCommand lastCommand = this.provider.createGetLastNetworkIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastNetworkIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastNetworkIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
