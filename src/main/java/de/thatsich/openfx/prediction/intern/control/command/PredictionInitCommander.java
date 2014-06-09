package de.thatsich.openfx.prediction.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.prediction.api.model.IPredictionState;
import de.thatsich.openfx.prediction.intern.control.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.InitBinaryPredictionsCommand;
import de.thatsich.openfx.prediction.intern.control.command.handler.GetLastBinaryPredictionIndexSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.command.handler.InitBinaryPredictionListSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.provider.IPredictionInitCommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Singleton
public class PredictionInitCommander
{
	private final Log log;
	private final IPredictionState state;
	private final IPredictionInitCommandProvider provider;

	@Inject
	public PredictionInitCommander(Log log, IPredictionState state, IPredictionInitCommandProvider provider)
	{
		this.log = log;
		this.state = state;
		this.provider = provider;

		this.init();
	}

	private void init()
	{
		this.initPredictionState();
		this.initPredictions();
	}

	private void initPredictionState()
	{
		final Path predictionInputFolderPath = Paths.get("io/predictions");
		this.state.path().set(predictionInputFolderPath);
		this.log.info("Set BinaryPredictionFolderPath in Model.");
	}

	/**
	 * Initialize BinaryPredictionList and pre-select last selected one
	 */
	private void initPredictions()
	{
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitBinaryPredictionsCommand initCommand = this.provider.createInitBinaryPredictionsCommand();
		initCommand.setOnSucceededCommandHandler(InitBinaryPredictionListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryPredictionList Retrieval.");

		final GetLastBinaryPredictionIndexCommand lastCommand = this.provider.createGetLastBinaryPredictionIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastBinaryPredictionIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastBinaryPredictionIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
