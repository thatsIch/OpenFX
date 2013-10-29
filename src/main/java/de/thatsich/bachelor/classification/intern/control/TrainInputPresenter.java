package de.thatsich.bachelor.classification.intern.control;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.command.TrainCommandProvider;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classification.intern.command.commands.RemoveBinaryClassificationCommand;
import de.thatsich.bachelor.classification.intern.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.classification.intern.model.BinaryClassifications;
import de.thatsich.bachelor.classification.intern.model.BinaryClassifiers;
import de.thatsich.bachelor.classification.intern.model.TrainState;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureVectorSets;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class TrainInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	@FXML Button nodeButtonTrainBinaryClassifier;
	@FXML Button nodeButtonRemoveBinaryClassifier;
	@FXML Button nodeButtonResetBinaryClassifierList;
	
	// Injects
	@Inject private TrainCommandProvider commander;
	@Inject private BinaryClassifiers binaryClassifiers;
	@Inject private BinaryClassifications binaryClassifications;
	@Inject private TrainState trainState;
	@Inject private FeatureVectorSets featureVectors;

	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override
	protected void initComponents() {
		this.initBinaryClassifierList();
	}

	@Override
	protected void bindComponents() {
		this.bindChoiceBoxBinaryClassifier();
		this.bindButtons();
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
	
	private void bindButtons() {
		this.nodeButtonTrainBinaryClassifier.disableProperty().bind(this.featureVectors.getSelectedFeatureVectorSetProperty().isNull());
		this.nodeButtonRemoveBinaryClassifier.disableProperty().bind(this.binaryClassifications.getSelectedBinaryClassificationProperty().isNull());
		this.nodeButtonResetBinaryClassifierList.disableProperty().bind(this.binaryClassifications.getBinaryClassificationListProperty().emptyProperty());
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================	
	@FXML private void onTrainBinaryClassifierAction() {
		final Path binaryClassifierFolderPath = this.trainState.getBinaryClassifierFolderPathProperty().get();
		final IBinaryClassifier selectedBinaryClassfier = this.binaryClassifiers.getSelectedBinaryClassifierProperty().get();
		final List<FeatureVectorSet> featureVectorSetList = this.featureVectors.getFeatureVectorSetListProperty().get();
		final FeatureVectorSet selectedFeatureVectorSet = this.featureVectors.getSelectedFeatureVectorSetProperty().get();

		final TrainBinaryClassifierSucceededHandler handler = new TrainBinaryClassifierSucceededHandler();
		final TrainBinaryClassifierCommand command = this.commander.createTrainBinaryClassifierCommand(binaryClassifierFolderPath, selectedBinaryClassfier, selectedFeatureVectorSet, featureVectorSetList);
		command.setOnSucceeded(handler);
		command.start();
	}
	
	@FXML private void onRemoveBinaryClassifierAction() {
		final IBinaryClassification selectedBinaryClassification = this.binaryClassifications.getSelectedBinaryClassificationProperty().get();
		
		final RemoveBinaryClassificationSucceededHandler handler = new RemoveBinaryClassificationSucceededHandler();
		final RemoveBinaryClassificationCommand command = this.commander.createRemoveBinaryClassificationCommand(selectedBinaryClassification);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("Commanded BinaryClassification Removal.");
	}
	
	@FXML private void onResetBinaryClassifierListAction() {
		final List<IBinaryClassification> binaryClassificationList = this.binaryClassifications.getBinaryClassificationListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(binaryClassificationList.size());
		this.log.info("Initialized Executor for resetting all Errors.");
		
		for (IBinaryClassification classification : binaryClassificationList) {
			final RemoveBinaryClassificationSucceededHandler handler = new RemoveBinaryClassificationSucceededHandler();
			final RemoveBinaryClassificationCommand command = this.commander.createRemoveBinaryClassificationCommand(classification);
			command.setOnSucceeded(handler);
			command.setExecutor(executor);
			command.start();
			this.log.info("File Deletion executed.");
		}
		
		executor.execute(new Runnable() {
			@Override public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
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
			final IBinaryClassification classifier = (IBinaryClassification) event.getSource().getValue();
			
			binaryClassifications.getBinaryClassificationListProperty().add(classifier);
			log.info("Added BinaryClassification to Database.");
			
			binaryClassifications.getSelectedBinaryClassificationProperty().set(classifier);
			log.info("Select BinaryClassifcation.");
		}
	}
	

	/**
	 * Handler for what should happen if the Command was successfull 
	 * for deleting the error
	 * 
	 * @author Minh
	 */
	private class RemoveBinaryClassificationSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final IBinaryClassification deletion = (IBinaryClassification) event.getSource().getValue();
			final ObservableList<IBinaryClassification> binaryClassificationList = binaryClassifications.getBinaryClassificationListProperty();
			
			binaryClassificationList.remove(deletion);
			log.info("Removed BinaryClassification from Database.");
			
			if (binaryClassificationList.size() > 0) {
				final IBinaryClassification first = binaryClassificationList.get(0);
				binaryClassifications.getSelectedBinaryClassificationProperty().set(first);
				log.info("Reset Selection to first BinaryClassifcation.");
			} else {
				binaryClassifications.getSelectedBinaryClassificationProperty().set(null);
				log.info("Reset Selection to null.");
			}
		}
	}
}
