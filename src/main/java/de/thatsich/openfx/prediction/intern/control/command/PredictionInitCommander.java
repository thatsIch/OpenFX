package de.thatsich.openfx.prediction.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.openfx.prediction.api.model.IPredictionState;
import de.thatsich.openfx.prediction.intern.control.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.InitBinaryPredictionListCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.InitPredictionFolderCommand;
import de.thatsich.openfx.prediction.intern.control.command.handler.GetLastBinaryPredictionIndexSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.command.handler.InitBinaryPredictionListSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.provider.IPredictionInitCommandProvider;
import de.thatsich.core.javafx.AInitCommander;
import de.thatsich.core.javafx.CommandExecutor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Singleton
public class PredictionInitCommander extends AInitCommander
{
	// Model
	@Inject private IPredictionState predictionState;

	// Command
	@Inject private IPredictionInitCommandProvider commander;

	/**
	 * Initialize BinaryPredictionList and pre-select last selected one
	 */
	@Inject
	private void initBinaryPredictionList()
	{
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
