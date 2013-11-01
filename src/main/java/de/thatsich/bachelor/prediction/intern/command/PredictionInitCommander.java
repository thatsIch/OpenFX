package de.thatsich.bachelor.prediction.intern.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.prediction.api.core.IPredictionState;
import de.thatsich.bachelor.prediction.intern.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitBinaryPredictionListCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitPredictionFolderCommand;
import de.thatsich.bachelor.prediction.intern.command.handler.GetLastBinaryPredictionIndexSucceededHandler;
import de.thatsich.bachelor.prediction.intern.command.handler.InitBinaryPredictionListSucceededHandler;
import de.thatsich.bachelor.prediction.intern.command.provider.IPredictionInitCommandProvider;
import de.thatsich.core.javafx.AInitCommader;
import de.thatsich.core.javafx.CommandExecutor;

@Singleton
public class PredictionInitCommander extends AInitCommader {
	
	// Model
	@Inject private IPredictionState predictionState;
	
	// Command
	@Inject private IPredictionInitCommandProvider commander;
	
	/**
	 * Initialize BinaryPredictionList and pre-select last selected one
	 */
	@Inject private void initBinaryPredictionList() {
		final Path predictionInputFolderPath = Paths.get("io/predictions");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		this.log.info("Prepared BinaryPrediction Preparations.");
		
		this.predictionState.setPredictionFolderPath(predictionInputFolderPath);
		this.log.info("Set BinaryPredictionFolderPath in Model.");
		
		final InitPredictionFolderCommand command = this.commander.createInitPredictionFolderCommand(predictionInputFolderPath);
		command.setExecutor(executor);
		command.start();
		this.log.info("Start PredictionFolder Creation.");
		
		final InitBinaryPredictionListCommand initCommand = this.commander.createInitBinaryPredictionListCommand(predictionInputFolderPath);
		initCommand.setOnSucceededCommandHandler(InitBinaryPredictionListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryPredictionList Retrieval.");
		
		final GetLastBinaryPredictionIndexCommand lastCommand = this.commander.createGetLastBinaryPredictionIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastBinaryPredictionIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastBinaryPredictionIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
