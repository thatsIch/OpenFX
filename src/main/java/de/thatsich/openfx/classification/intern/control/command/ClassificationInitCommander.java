package de.thatsich.openfx.classification.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.classification.api.model.IClassificationState;
import de.thatsich.openfx.classification.intern.control.command.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.InitBinaryClassificationsCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.InitBinaryClassifierListCommand;
import de.thatsich.openfx.classification.intern.control.handler.GetLastBinaryClassificationIndexSucceededHandler;
import de.thatsich.openfx.classification.intern.control.handler.GetLastBinaryClassifierIndexSucceededHandler;
import de.thatsich.openfx.classification.intern.control.handler.InitBinaryClassificationListSucceededHandler;
import de.thatsich.openfx.classification.intern.control.handler.InitBinaryClassifierListSucceededHandler;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationInitCommandProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;


@Singleton
public class ClassificationInitCommander
{
	@Inject private Log log;

	@Inject private IClassificationInitCommandProvider commander;
	@Inject private IClassificationState trainState;

	@Inject
	private void init()
	{
		this.initBinaryClassifierList();
		this.initBinaryClassificationList();
	}

	/**
	 * Initialize the List of BinaryClassifiers and preselects the last selected
	 * one.
	 */
	private void initBinaryClassifierList()
	{
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitBinaryClassifierListCommand initCommand = this.commander.createInitBinaryClassifierListCommand();
		initCommand.setOnSucceededCommandHandler(InitBinaryClassifierListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryClassifierList Retrieval.");

		final GetLastBinaryClassifierIndexCommand lastCommand = this.commander.createGetLastBinaryClassifierIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastBinaryClassifierIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastBinaryClassifierIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Initialize the List of BinaryClassification and preselects the last
	 * selected one.
	 */
	private void initBinaryClassificationList()
	{
		final Path folderPath = Paths.get("io/binaryclassifier");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		this.trainState.path().set(folderPath);
		this.log.info("Set ClassificationInputFolderPath to Model.");

		final InitBinaryClassificationsCommand initCommand = this.commander.createInitBinaryClassificationListCommand(folderPath);
		initCommand.setOnSucceededCommandHandler(InitBinaryClassificationListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryClassificationList Retrieval.");

		final GetLastBinaryClassificationIndexCommand lastCommand = this.commander.createGetLastBinaryClassificationIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastBinaryClassificationIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastBinaryClassificationIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
