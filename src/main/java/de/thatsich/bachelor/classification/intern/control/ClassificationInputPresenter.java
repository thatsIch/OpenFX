package de.thatsich.bachelor.classification.intern.control;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.core.IBinaryClassifiers;
import de.thatsich.bachelor.classification.api.core.IClassificationState;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.ClassificationInitCommander;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.commands.RemoveBinaryClassificationCommand;
import de.thatsich.bachelor.classification.intern.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.classification.intern.command.provider.IClassificationCommandProvider;
import de.thatsich.bachelor.classification.intern.control.handler.RemoveBinaryClassificationSucceededHandler;
import de.thatsich.bachelor.classification.intern.control.handler.TrainBinaryClassifierSucceededHandler;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class ClassificationInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	@FXML Button nodeButtonTrainBinaryClassifier;
	@FXML Button nodeButtonRemoveBinaryClassifier;
	@FXML Button nodeButtonResetBinaryClassifierList;
	
	// Injects
	@Inject private IClassificationCommandProvider commander;
	
	@Inject private IBinaryClassifiers binaryClassifiers;
	@Inject private IBinaryClassifications binaryClassifications;
	@Inject private IClassificationState trainState;
	@Inject private IFeatureVectorSets featureVectors;

	@Inject ClassificationInitCommander initCommander;
	
	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override
	protected void initComponents() {
		
	}

	@Override
	protected void bindComponents() {
		this.bindChoiceBoxBinaryClassifier();
		this.bindButtons();
	}

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
		final Path binaryClassifierFolderPath = this.trainState.getBinaryClassifierFolderPath();
		final IBinaryClassifier selectedBinaryClassfier = this.binaryClassifiers.getSelectedBinaryClassifier();
		final List<FeatureVectorSet> featureVectorSetList = this.featureVectors.getFeatureVectorSetListProperty();
		final FeatureVectorSet selectedFeatureVectorSet = this.featureVectors.getSelectedFeatureVectorSet();

		final TrainBinaryClassifierCommand command = this.commander.createTrainBinaryClassifierCommand(binaryClassifierFolderPath, selectedBinaryClassfier, selectedFeatureVectorSet, featureVectorSetList);
		command.setOnSucceededCommandHandler(TrainBinaryClassifierSucceededHandler.class);
		command.start();
	}
	
	@FXML private void onRemoveBinaryClassifierAction() {
		final IBinaryClassification selectedBinaryClassification = this.binaryClassifications.getSelectedBinaryClassification();
		
		final RemoveBinaryClassificationCommand command = this.commander.createRemoveBinaryClassificationCommand(selectedBinaryClassification);
		command.setOnSucceededCommandHandler(RemoveBinaryClassificationSucceededHandler.class);
		command.start();
		this.log.info("Commanded BinaryClassification Removal.");
	}
	
	@FXML private void onResetBinaryClassifierListAction() {
		final List<IBinaryClassification> binaryClassificationList = this.binaryClassifications.getBinaryClassificationListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(binaryClassificationList.size());
		this.log.info("Initialized Executor for resetting all Errors.");
		
		for (IBinaryClassification classification : binaryClassificationList) {
			final RemoveBinaryClassificationCommand command = this.commander.createRemoveBinaryClassificationCommand(classification);
			command.setOnSucceededCommandHandler(RemoveBinaryClassificationSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("File Deletion executed.");
		}
		
		executor.execute(new Runnable() { @Override public void run() { System.gc(); } });
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
