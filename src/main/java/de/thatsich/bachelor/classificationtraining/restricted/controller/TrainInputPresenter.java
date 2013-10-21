package de.thatsich.bachelor.classificationtraining.restricted.controller;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.classificationtraining.restricted.models.BinaryClassifiers;
import de.thatsich.bachelor.classificationtraining.restricted.services.TrainCommandService;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureVectorSets;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class TrainInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	
	// Injects
	@Inject private TrainCommandService commander;
	@Inject private BinaryClassifiers binaryClassifiers;
	@Inject private FeatureVectorSets featureVectors;

	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override public void initialize(URL location, ResourceBundle resource) {
		this.bindChoiceBoxBinaryClassifier();
		
		this.initBinaryClassifierList();
	}
	
	/**
	 * Initialize the List of BinaryClassifiers and preselects the last selected one.
	 */
	private void initBinaryClassifierList() {
		final InitBinaryClassifierListSucceededHandler initHandler = new InitBinaryClassifierListSucceededHandler();
		final GetLastBinaryClassifierIndexSucceededHandler lastHandler = new GetLastBinaryClassifierIndexSucceededHandler(); 
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);

		final InitBinaryClassifierListCommand initCommand =	this.commander.createInitBinaryClassifierListCommand();
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryClassifierList Retrieval.");
		
		final GetLastBinaryClassifierIndexCommand lastCommand = this.commander.createGetLastBinaryClassifierIndexCommand();
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastBinaryClassifierIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the BinaryClassifierList
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitBinaryClassifierListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<IBinaryClassifier> classifierList = (List<IBinaryClassifier>) event.getSource().getValue();
			
			binaryClassifiers.getBinaryClassifierListProperty().addAll(classifierList);
			log.info("Added BinaryClassifier to Database.");
		}
	}	
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting LastBinaryClassifierIndex
	 * 
	 * @author Minh
	 */
	private class GetLastBinaryClassifierIndexSucceededHandler implements EventHandler<WorkerStateEvent> {

		@Override
		public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastBinaryClassifierIndex.");
			
			if (commandResult != null) {
				final IBinaryClassifier selected = binaryClassifiers.getBinaryClassifierListProperty().get(commandResult);
				binaryClassifiers.getSelectedBinaryClassifierProperty().set(selected);
				log.info("Set LastBinaryClassifierIndex in Model.");
			}
		}
	}
	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	/**
	 * Bind ChoiceBoxBinaryClassifier to the Model.
	 */
	private void bindChoiceBoxBinaryClassifier() {
		this.nodeChoiceBoxBinaryClassifier.setConverter(new StringConverter<IBinaryClassifier>() {
			@Override public String toString(IBinaryClassifier bc) { return bc.getName(); }
			@Override public IBinaryClassifier fromString(String string) { return null; }
		});
		this.log.info("Set up ChoiceBoxBinaryClassifier for proper name display.");
		
		this.nodeChoiceBoxBinaryClassifier.itemsProperty().bindBidirectional(this.binaryClassifiers.getBinaryClassifierListProperty());
		this.nodeChoiceBoxBinaryClassifier.valueProperty().bindBidirectional(this.binaryClassifiers.getSelectedBinaryClassifierProperty());
		this.log.info("Bound ChoiceBoxBinaryClassifier to Model.");
		
		this.nodeChoiceBoxBinaryClassifier.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				final SetLastBinaryClassifierIndexCommand lastCommand = commander.createSetLastBinaryClassifierIndexCommand(newValue.intValue());
				lastCommand.start();
			}
		});
		this.log.info("Bound ChoiceBoxBinaryClassifier to Config.");
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================	
	@FXML private void onTrainBinaryClassifierAction() {
		final IBinaryClassifier selectedBinaryClassfier = this.binaryClassifiers.getSelectedBinaryClassifierProperty().get();
		final List<FeatureVectorSet> featureVectorSetList = this.featureVectors.getFeatureVectorSetListProperty().get();
		final FeatureVectorSet selectedFeatureVectorSet = this.featureVectors.getSelectedFeatureVectorSetProperty().get();
		
		if (selectedFeatureVectorSet == null) throw new InvalidParameterException("SelectedFeatureVector is null.");
		if (featureVectorSetList == null) throw new InvalidParameterException("FeatureVectorList is null.");
		if (selectedBinaryClassfier == null) throw new InvalidParameterException("SelectedBinaryClassifier is null.");
		
		final TrainBinaryClassifierSucceededHandler handler = new TrainBinaryClassifierSucceededHandler();
		final TrainBinaryClassifierCommand command = this.commander.createTrainBinaryClassifierCommand(selectedBinaryClassfier, selectedFeatureVectorSet, featureVectorSetList);
		command.setOnSucceeded(handler);
		command.start();
	}
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for training BinaryClassifier
	 * 
	 * @author Minh
	 */
	private class TrainBinaryClassifierSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
//			final BinaryClassifier classifier = (BinaryClassifier) event.getSource().getValue();
//			
//			featureVectors.getFeatureVectorListProperty().get().addAll(fv);
//			log.info("Added FeatureVector to Database.");
		}
	}
}
