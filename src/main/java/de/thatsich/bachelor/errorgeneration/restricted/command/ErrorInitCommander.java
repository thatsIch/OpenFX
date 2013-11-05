package de.thatsich.bachelor.errorgeneration.restricted.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorState;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.InitErrorGeneratorListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.handler.GetLastErrorEntryIndexSucceededHandler;
import de.thatsich.bachelor.errorgeneration.restricted.command.handler.GetLastErrorGeneratorIndexSucceededHandler;
import de.thatsich.bachelor.errorgeneration.restricted.command.handler.GetLastErrorLoopCountSucceededHandler;
import de.thatsich.bachelor.errorgeneration.restricted.command.handler.InitErrorEntryListSucceededHandler;
import de.thatsich.bachelor.errorgeneration.restricted.command.handler.InitErrorGeneratorListSucceededHandler;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;

@Singleton
public class ErrorInitCommander {
	@Inject private Log log;
	
	@Inject private IErrorState errorState;
	
	@Inject private IErrorInitCommandProvider commander;
	
	@Inject private void init() {
		this.initErrorLoopCount();
		this.initErrorGeneratorList();
		this.initErrorEntryList();
	}
	
	/**
	 * Fetches the last ErrorLoopCount
	 */
	private void initErrorLoopCount() {
		final GetLastErrorCountCommand command = this.commander.createGetLastErrorCountCommand();
		command.setOnSucceededCommandHandler(GetLastErrorLoopCountSucceededHandler.class);
		command.start();
	}
	
	/**
	 * Initialize the ErrorGeneratorList and preselects the last selected one
	 */
	private void initErrorGeneratorList() {
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		final InitErrorGeneratorListCommand initCommand = this.commander.createInitErrorGeneratorListCommand();
		initCommand.setOnSucceededCommandHandler(InitErrorGeneratorListSucceededHandler.class);
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
	private void initErrorEntryList() {
		final Path errorInputFolderPath = Paths.get("io/error");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.errorState.setErrorEntryFolderPath(errorInputFolderPath);
		this.log.info("Set ErrorInputFolderPath to Model.");
		
		final InitErrorEntryListCommand initCommand = this.commander.createInitErrorEntryListCommand(errorInputFolderPath);
		initCommand.setOnSucceededCommandHandler(InitErrorEntryListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized ErrorEntryList Retrieval.");
		
		final GetLastErrorEntryIndexCommand lastCommand = this.commander.createGetLastErrorEntryIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastErrorEntryIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastErrorEntryIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}