package de.thatsich.openfx.imageprocessing.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.GetLastImageEntryIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.GetLastLocationCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.InitImageEntryListCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.handler.GetLastImageEntryIndexSucceededHandler;
import de.thatsich.openfx.imageprocessing.intern.control.command.handler.GetLastLocationSucceededHandler;
import de.thatsich.openfx.imageprocessing.intern.control.command.handler.InitImagesSucceededHandler;
import de.thatsich.openfx.imageprocessing.intern.control.command.provider.IImageInitCommandProvider;

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

		this.imageState.imageFolder().set(imageInputPath);
		this.log.info("Set ImageInputFolderPath to Model.");

		final InitImageEntryListCommand initCommand = this.commander.createInitImageEntryListCommand(imageInputPath);
		initCommand.setOnSucceededCommandHandler(InitImagesSucceededHandler.class);
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
