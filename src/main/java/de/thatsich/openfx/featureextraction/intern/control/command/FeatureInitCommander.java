package de.thatsich.openfx.featureextraction.intern.control.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastTileSizeCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeatureExtractorsCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeaturesCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.handler.GetLastFeatureExtractorIndexSucceededHandler;
import de.thatsich.openfx.featureextraction.intern.control.command.handler.GetLastTileSizeSucceededHandler;
import de.thatsich.openfx.featureextraction.intern.control.command.handler.InitFeatureExtractorListSucceededHandler;
import de.thatsich.openfx.featureextraction.intern.control.command.handler.InitFeaturesSucceededHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Singleton
public class FeatureInitCommander
{

	// Injects
	@Inject
	private Log log;

	// Model
	@Inject
	private IFeatureState featureState;

	// Command
	@Inject
	private IFeatureInitCommandProvider commander;
	//	@Inject private FeatureCommandProvider commander;

	@Inject
	private void initialize()
	{
		this.initFrameSize();

		this.initFeatureExtractorList();
		this.initFeatureVectorList();
	}

	/**
	 * Fetches last FrameSize
	 */
	private void initFrameSize()
	{
		final GetLastTileSizeCommand command = this.commander.createGetLastFrameSizeCommand();
		command.setOnSucceededCommandHandler(GetLastTileSizeSucceededHandler.class);
		command.start();
		this.log.info("Initialized LastFrameSize Retrieval.");
	}

	/**
	 * Gets FeatureExtractorList and preselects last selected one.
	 */
	private void initFeatureExtractorList()
	{
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitFeatureExtractorsCommand initCommand = this.commander.createInitFeatureExtractorListCommand();
		initCommand.setOnSucceededCommandHandler(InitFeatureExtractorListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized FeatureExtractorList Retrieval.");

		final GetLastFeatureExtractorIndexCommand lastCommand = this.commander.createGetLastFeatureExtractorIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastFeatureExtractorIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastFeatureExtractorIndex Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	private void initFeatureVectorList()
	{
		final Path folderPath = Paths.get("io/features");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		this.featureState.path().set(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");

		final InitFeaturesCommand initCommand = this.commander.createInitFeatureVectorListCommand(folderPath);
		initCommand.setOnSucceededCommandHandler(InitFeaturesSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized InitFeatureVectorList Retrieval.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
