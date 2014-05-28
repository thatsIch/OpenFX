package de.thatsich.bachelor.imageprocessing.intern.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.bachelor.imageprocessing.api.core.IImageState;
import de.thatsich.bachelor.imageprocessing.intern.command.commands.GetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.intern.command.commands.GetLastLocationCommand;
import de.thatsich.bachelor.imageprocessing.intern.command.commands.InitImageEntryListCommand;
import de.thatsich.bachelor.imageprocessing.intern.command.handler.GetLastImageEntryIndexSucceededHandler;
import de.thatsich.bachelor.imageprocessing.intern.command.handler.GetLastLocationSucceededHandler;
import de.thatsich.bachelor.imageprocessing.intern.command.handler.InitImageEntryListSucceededHandler;
import de.thatsich.bachelor.imageprocessing.intern.command.provider.IImageInitCommandProvider;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Singleton
public class ImageInitCommander
{

	@Inject
	private Log log;

	@Inject
	private IImageState imageState;
	@Inject
	private IImageInitCommandProvider commander;

	/**
	 * Initialize all ImageEntries and Preselection of the last selected ImageEntry
	 */
	@Inject
	private void initTableViewImageEntryList()
	{
		final Path imageInputPath = Paths.get("io/input");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		this.imageState.imageFolderPathProperty().set(imageInputPath);
		this.log.info("Set ImageInputFolderPath to Model.");

		final InitImageEntryListCommand initCommand = this.commander.createInitImageEntryListCommand(imageInputPath);
		initCommand.setOnSucceededCommandHandler(InitImageEntryListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized ImageEntryList Retrieval.");

		final GetLastImageEntryIndexCommand lastCommand = this.commander.createGetLastImageEntryIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastImageEntryIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastImageEntryIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	@Inject
	private void initImageFileChooserLastLocation()
	{
		final GetLastLocationCommand command = this.commander.createGetLastLocationCommand();
		command.setOnSucceededCommandHandler(GetLastLocationSucceededHandler.class);
		command.start();
		this.log.info("Initialized LastLocation Retrieval.");
	}
}
