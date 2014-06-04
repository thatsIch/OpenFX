package de.thatsich.openfx.preprocessing.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessingState;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.GetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.GetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.InitPreProcessingListCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.InitPreProcessorListCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.handler.GetLastPreProcessingIndexSucceededHandler;
import de.thatsich.openfx.preprocessing.intern.control.command.handler.GetLastPreProcessorIndexSucceededHandler;
import de.thatsich.openfx.preprocessing.intern.control.command.handler.InitPreProcessingListSucceededHandler;
import de.thatsich.openfx.preprocessing.intern.control.command.handler.InitPreProcessorListSucceededHandler;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingInitCommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;


@Singleton
public class PreProcessingInitCommander
{
	// Injects
	@Inject
	private Log log;

	@Inject
	private IPreProcessingInitCommandProvider commander;
	@Inject
	private IPreProcessingState state;

	@Inject
	private void init()
	{
		this.initPreProcessorList();
		this.initPreProcessingList();
	}

	private void initPreProcessorList()
	{
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitPreProcessorListCommand initCommand = this.commander.createInitPreProcessorListCommand();
		initCommand.setOnSucceededCommandHandler(InitPreProcessorListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized " + initCommand + " for PPs Retrieval.");

		final GetLastPreProcessorIndexCommand lastCommand = this.commander.createGetLastPreProcessorIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastPreProcessorIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized " + lastCommand + " for index retrieval.");

		executor.shutdown();
		this.log.info("Shutting down " + executor);
	}

	private void initPreProcessingList()
	{
		final Path path = Paths.get("io/preprocessing");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		this.state.path().set(path);
		this.log.info("Set " + path + " to Model.");

		final InitPreProcessingListCommand initCommand = this.commander.createInitPreProcessingListCommand(path);
		initCommand.setOnSucceededCommandHandler(InitPreProcessingListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized " + initCommand);

		final GetLastPreProcessingIndexCommand lastCommand = this.commander.createGetLastPreProcessingIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastPreProcessingIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized " + lastCommand);

		executor.shutdown();
		this.log.info("Shutting down " + executor);
	}
}
