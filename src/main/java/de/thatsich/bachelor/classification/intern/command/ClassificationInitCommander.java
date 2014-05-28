package de.thatsich.bachelor.classification.intern.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.bachelor.classification.api.models.IClassificationState;
import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassificationListCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classification.intern.command.handler.GetLastBinaryClassificationIndexSucceededHandler;
import de.thatsich.bachelor.classification.intern.command.handler.GetLastBinaryClassifierIndexSucceededHandler;
import de.thatsich.bachelor.classification.intern.command.handler.InitBinaryClassificationListSucceededHandler;
import de.thatsich.bachelor.classification.intern.command.handler.InitBinaryClassifierListSucceededHandler;
import de.thatsich.bachelor.classification.intern.command.provider.IClassificationInitCommandProvider;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;


@Singleton
public class ClassificationInitCommander
{
	@Inject
	private Log log;

	@Inject
	private IClassificationInitCommandProvider commander;
	@Inject
	private IClassificationState trainState;

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

		this.trainState.getBinaryClassifierFolderPathProperty().set(folderPath);
		this.log.info("Set ClassificationInputFolderPath to Model.");

		final InitBinaryClassificationListCommand initCommand = this.commander.createInitBinaryClassificationListCommand(folderPath);
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
