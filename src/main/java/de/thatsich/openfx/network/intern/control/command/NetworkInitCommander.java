package de.thatsich.openfx.network.intern.control.command;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.network.api.model.INetworkState;
import de.thatsich.openfx.network.intern.control.command.commands.GetLastNetworkIndexCommand;
import de.thatsich.openfx.network.intern.control.command.commands.InitNetworksCommand;
import de.thatsich.openfx.network.intern.control.handler.GetLastNetworkIndexSucceededHandler;
import de.thatsich.openfx.network.intern.control.handler.InitNetworkListSucceededHandler;
import de.thatsich.openfx.network.intern.control.provider.INetworkInitCommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkInitCommander
{
	@Inject private Log log;
	@Inject private INetworkState state;
	@Inject private INetworkInitCommandProvider provider;

	@Inject
	private void initNetworkList()
	{
		final Path networkInputPath = Paths.get("io/networks");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		this.log.info("Prepared Network Preparations.");

		this.state.path().set(networkInputPath);
		this.log.info("Set Network Path in Model.");

		final InitNetworksCommand initCommand = this.provider.createInitNetworksCommand();
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
