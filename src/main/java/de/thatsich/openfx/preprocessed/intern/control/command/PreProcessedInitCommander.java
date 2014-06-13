package de.thatsich.openfx.preprocessed.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.preprocessed.api.model.IPreProcessedState;
import de.thatsich.openfx.preprocessed.intern.control.command.commands.InitPreProcessedsCommand;
import de.thatsich.openfx.preprocessed.intern.control.command.handler.InitPreProcessedsSucceededHandler;
import de.thatsich.openfx.preprocessed.intern.control.command.provider.IPreProcessedInitCommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;


@Singleton
public class PreProcessedInitCommander
{
	// Injects
	@Inject private Log log;
	@Inject private IPreProcessedInitCommandProvider provider;
	@Inject private IPreProcessedState state;

	@Inject
	private void init()
	{
		this.initPreProcesseds();
	}

	private void initPreProcesseds()
	{
		final Path path = Paths.get("io/preprocesseds");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		this.state.path().set(path);
		this.log.info("Set " + path + " to Model.");

		final InitPreProcessedsCommand initCommand = this.provider.createInitPreProcessedsCommand();
		initCommand.setOnSucceededCommandHandler(InitPreProcessedsSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized " + initCommand);

		executor.shutdown();
		this.log.info("Shutting down " + executor);
	}
}
