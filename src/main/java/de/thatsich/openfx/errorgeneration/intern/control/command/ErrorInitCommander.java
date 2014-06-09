package de.thatsich.openfx.errorgeneration.intern.control.command;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.InitErrorGeneratorsCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.InitErrorsCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.handler.GetLastErrorGeneratorIndexSucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.command.handler.GetLastErrorIndexSucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.command.handler.GetLastErrorLoopCountSucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.command.handler.InitErrorGeneratorsSucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.command.handler.InitErrorsSucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorInitCommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

public class ErrorInitCommander
{
	@Inject private Log log;
	@Inject private IErrorState errorState;
	@Inject private IErrorInitCommandProvider commander;

	@Inject
	private void init()
	{
		this.initErrorState();
		this.initErrorGenerators();
		this.initErrors();
	}

	/**
	 * Init the stat of error tab
	 */
	private void initErrorState()
	{
		this.initErrorPath();
		this.initErrorLoopCount();
	}

	/**
	 * Initialize the ErrorGeneratorList and preselects the last selected one
	 */
	private void initErrorGenerators()
	{
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitErrorGeneratorsCommand initCommand = this.commander.createInitErrorGeneratorsCommand();
		initCommand.setOnSucceededCommandHandler(InitErrorGeneratorsSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized ErrorGeneratorList Retrieval.");

		final GetLastErrorGeneratorIndexCommand lastCommand = this.commander.createGetLastErrorGeneratorIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastErrorGeneratorIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastErrorGeneratorIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Set up ErrorEntryList and preselects last selected one
	 */
	private void initErrors()
	{
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitErrorsCommand initCommand = this.commander.createInitErrorsCommand();
		initCommand.setOnSucceededCommandHandler(InitErrorsSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized ErrorEntryList Retrieval.");

		final GetLastErrorIndexCommand lastCommand = this.commander.createGetLastErrorIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastErrorIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastErrorEntryIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Sets the folder where all errors are saved to
	 */
	private void initErrorPath()
	{
		final Path errorPath = Paths.get("io/errors");
		this.errorState.path().set(errorPath);
		this.log.info("Set ErrorPath in Model.");
	}

	/**
	 * Fetches the last ErrorLoopCount
	 */
	private void initErrorLoopCount()
	{
		final GetLastErrorCountCommand command = this.commander.createGetLastErrorCountCommand();
		command.setOnSucceededCommandHandler(GetLastErrorLoopCountSucceededHandler.class);
		command.start();
	}
}
