package de.thatsich.bachelor.classificationtraining.restricted.controller;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.TrainedBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastTrainedBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitTrainedBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.models.TrainState;
import de.thatsich.bachelor.classificationtraining.restricted.models.TrainedBinaryClassifiers;
import de.thatsich.bachelor.classificationtraining.restricted.services.TrainCommandService;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class TrainListPresenter extends AFXMLPresenter {

	@Inject private TrainedBinaryClassifiers trainedBinaryClassifiers;
	@Inject private TrainState trainState;
	@Inject private TrainCommandService commander;
	
	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override public void initialize(URL location, ResourceBundle resource) {
		this.initTrainedBinaryClassifierList();
	}
	
	private void initTrainedBinaryClassifierList() {
		final Path folderPath = Paths.get("io/binaryclassifier");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.trainState.getBinaryClassifierFolderPathProperty().set(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");
		
		final InitTrainedBinaryClassifierListSucceededHandler initHandler = new InitTrainedBinaryClassifierListSucceededHandler();
		final InitTrainedBinaryClassifierListCommand initCommand = this.commander.createInitTrainedBinaryClassifierListCommand(folderPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized InitTrainedBinaryClassifierList Retrieval.");
		
		final GetLastTrainedBinaryClassifierIndexSucceededHandler lastHandler = new GetLastTrainedBinaryClassifierIndexSucceededHandler(); 
		final GetLastTrainedBinaryClassifierIndexCommand lastCommand = this.commander.createGetLastTrainedBinaryClassifierIndexCommand();
		lastCommand.setExecutor(executor);
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.start();
		this.log.info("Initialized GetLastTrainedBinaryClassifierIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature vector list
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitTrainedBinaryClassifierListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<TrainedBinaryClassifier> trainedBinaryClassifierList = (List<TrainedBinaryClassifier>) event.getSource().getValue();
			
			trainedBinaryClassifiers.getTrainedBinaryClassifierListProperty().addAll(trainedBinaryClassifierList);
			log.info("Added TrainedBinaryClassifierList to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the LastFeatureVectorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastTrainedBinaryClassifierIndexSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastTrainedBinaryClassifierIndex.");
			
			if (commandResult != null && commandResult >= 0) {
//				nodeTreeViewFeatureVectorSetList.getSelectionModel().select(commandResult);
//				log.info("Set LastFeatureVectorIndex in TreeView.");
//			} else {
//				log.info("commandResult != null: " + (commandResult != null));
//				log.info("commandResult >= 0: " + (commandResult >= 0));
//				log.info("size > commandResult: " + (nodeTreeViewFeatureVectorSetList.getChildrenUnmodifiable().size() > commandResult));
//				log.info("with size " + nodeTreeViewFeatureVectorSetList.getChildrenUnmodifiable().size());
//				log.info("with commandResult " + commandResult);
			}
		}
	}
}
