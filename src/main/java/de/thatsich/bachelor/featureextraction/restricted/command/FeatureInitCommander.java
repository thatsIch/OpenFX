package de.thatsich.bachelor.featureextraction.restricted.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.InitFeatureVectorSetListCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.core.javafx.CommandExecutor;

@Singleton
public class FeatureInitCommander {

	// Injects
	@Inject private Log log;
	
	// Model
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private IFeatureState featureState;
	@Inject private IFeatureVectorSets featureVectors;
	
	// Command
	@Inject private FeatureCommandProvider commander;
	
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
		command.setOnSucceededCommandHandler(new GetLastFrameSizeSucceededHandler());
		command.start();
		this.log.info("Initialized LastFrameSize Retrieval.");
	}
	
	/**
	 * Gets FeatureExtractorList and preselects last selected one.
	 */
	private void initFeatureExtractorList() {
		final InitFeatureExtractorListSucceededHandler initHandler = new InitFeatureExtractorListSucceededHandler();
		final GetLastFeatureExtractorIndexSucceededHandler lastHandler = new GetLastFeatureExtractorIndexSucceededHandler(); 
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		final InitFeatureExtractorListCommand initCommand =	this.commander.createInitFeatureExtractorListCommand();
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized FeatureExtractorList Retrieval.");
		
		final GetLastFeatureExtractorIndexCommand lastCommand = this.commander.createGetLastFeatureExtractorIndexCommand();
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastFeatureExtractorIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	private void initFeatureVectorList() {
		final Path folderPath = Paths.get("io/featurevectors");
		final InitFeatureVectorListSucceededHandler initHandler = new InitFeatureVectorListSucceededHandler();
		final GetLastFeatureSpaceIndexSucceededHandler lastHandler = new GetLastFeatureSpaceIndexSucceededHandler();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.featureState.setFeatureVectorFolderPath(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");
		
		final InitFeatureVectorSetListCommand initCommand = this.commander.createInitFeatureVectorListCommand(folderPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized InitFeatureVectorList Retrieval.");
		
		final GetLastFeatureVectorIndexCommand lastCommand = this.commander.createGetLastFeatureVectorIndexCommand();
		lastCommand.setExecutor(executor);
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.start();
		this.log.info("Initialized GetLastFeatureVectorIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting LastFrameSize
	 * 
	 * @author Minh
	 */
	private class GetLastFrameSizeSucceededHandler extends ACommandHandler<Integer> {
		@Override public void handle(Integer value) {
			if (value != null) {
				featureState.setFrameSize(value);
				log.info("Set LastErrorLoopCount in Model.");
			}
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature extractor list
	 * 
	 * @author Minh
	 */
	private class InitFeatureExtractorListSucceededHandler extends ACommandHandler<List<IFeatureExtractor>> {
		@Override
		public void handle(List<IFeatureExtractor> list) {
			featureExtractors.getFeatureExtractorsProperty().addAll(list);
			log.info("Added FeatureExtractor to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting LastFeatureExtractorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastFeatureExtractorIndexSucceededHandler extends ACommandHandler<Integer> {
		@Override public void handle(Integer value) {
			if (value != null) {
				final IFeatureExtractor selectedFeatureExtractor = featureExtractors.getFeatureExtractorsProperty().get(value);
				featureExtractors.getSelectedFeatureExtractorProperty().set(selectedFeatureExtractor);
				log.info("Set LastSelectedFeatureExtractor in Model.");
			}
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature vector list
	 * 
	 * @author Minh
	 */
	private class InitFeatureVectorListSucceededHandler extends ACommandHandler<List<FeatureVectorSet>> {
		@Override
		public void handle(List<FeatureVectorSet> featureVectorList) {
			featureVectors.getFeatureVectorSetListProperty().addAll(featureVectorList);
			log.info("Added FeatureExtractor to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the LastFeatureVectorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastFeatureSpaceIndexSucceededHandler extends ACommandHandler<Integer> {
		@Override public void handle(Integer value) {
			featureVectors.setSelectedIndex(value);
			log.info("Set SelectedIndex.");
		}
	}
	
	public void dummy() {
		
	}
}
