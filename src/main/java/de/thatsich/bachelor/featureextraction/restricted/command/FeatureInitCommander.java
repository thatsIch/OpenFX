package de.thatsich.bachelor.featureextraction.restricted.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.InitFeatureVectorSetListCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.handler.GetLastFeatureExtractorIndexSucceededHandler;
import de.thatsich.bachelor.featureextraction.restricted.command.handler.GetLastFeatureSpaceIndexSucceededHandler;
import de.thatsich.bachelor.featureextraction.restricted.command.handler.GetLastFrameSizeSucceededHandler;
import de.thatsich.bachelor.featureextraction.restricted.command.handler.InitFeatureExtractorListSucceededHandler;
import de.thatsich.bachelor.featureextraction.restricted.command.handler.InitFeatureVectorListSucceededHandler;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.CommandExecutor;

@Singleton
public class FeatureInitCommander {

	// Injects
	@Inject private Log log;
	
	// Model
	@Inject private IFeatureState featureState;
	
	// Command
	@Inject private IFeatureInitCommandProvider commander;
//	@Inject private FeatureCommandProvider commander;
	
	@Inject private void initialize() {
		this.initFrameSize();
		
		this.initFeatureExtractorList();
		this.initFeatureVectorList();
	}
	
	/**
	 * Fetches last FrameSize
	 */
	private void initFrameSize() { 
		final GetLastFrameSizeCommand command = this.commander.createGetLastFrameSizeCommand();
		command.setOnSucceededCommandHandler(GetLastFrameSizeSucceededHandler.class);
		command.start();
		this.log.info("Initialized LastFrameSize Retrieval.");
	}
	
	/**
	 * Gets FeatureExtractorList and preselects last selected one.
	 */
	private void initFeatureExtractorList() {
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		final InitFeatureExtractorListCommand initCommand =	this.commander.createInitFeatureExtractorListCommand();
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
	
	private void initFeatureVectorList() {
		final Path folderPath = Paths.get("io/featurevectors");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.featureState.setFeatureVectorFolderPath(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");
		
		final InitFeatureVectorSetListCommand initCommand = this.commander.createInitFeatureVectorListCommand(folderPath);
		initCommand.setOnSucceededCommandHandler(InitFeatureVectorListSucceededHandler.class);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized InitFeatureVectorList Retrieval.");
		
		final GetLastFeatureVectorIndexCommand lastCommand = this.commander.createGetLastFeatureVectorIndexCommand();
		lastCommand.setOnSucceededCommandHandler(GetLastFeatureSpaceIndexSucceededHandler.class);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized GetLastFeatureVectorIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
