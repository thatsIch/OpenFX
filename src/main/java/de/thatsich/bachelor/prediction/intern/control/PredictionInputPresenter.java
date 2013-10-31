package de.thatsich.bachelor.prediction.intern.control;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.core.IPredictionState;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.command.BinaryPredictionCommandProvider;
import de.thatsich.bachelor.prediction.intern.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.TestBinaryClassificationCommand;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class PredictionInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private Button nodeButtonPredictBinaryClassification;
	@FXML private Button nodeButtonDeleteBinaryPrediction;
	@FXML private Button nodeButtonResetBinaryPrediction;
	
	// Injects
	@Inject private IImageEntries imageEntries;
	@Inject private IErrorGenerators errorGenerators;
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private IBinaryClassifications binaryClassifications;
	@Inject private IPredictionState predictionState;
	@Inject private IBinaryPredictions binaryPredictions;
	
	@Inject private BinaryPredictionCommandProvider commander;
	
	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override
	protected void initComponents() {
	}

	@Override
	protected void bindComponents() {
		this.bindButtons();
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	private void bindButtons() {
		this.nodeButtonPredictBinaryClassification.disableProperty().bind(this.imageEntries.selectedImageEntryProperty().isNull().or(this.binaryClassifications.getSelectedBinaryClassificationProperty().isNull()));
		this.nodeButtonDeleteBinaryPrediction.disableProperty().bind(this.binaryPredictions.getSelectedBinaryPredictionProperty().isNull());
		this.nodeButtonResetBinaryPrediction.disableProperty().bind(this.binaryPredictions.getBinaryPredictionListProperty().emptyProperty());
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	
	/**
	 * 
	 */
	@FXML private void onPredictBinaryPredictionAction() {
		final Path predictionFolderPath = this.predictionState.getPredictionFolderPathProperty().get();
		final IBinaryClassification binaryClassification = this.binaryClassifications.getSelectedBinaryClassification();
		final String errorGeneratorName = binaryClassification.getErrorNameProperty().get();
		final String featureExtractorName = binaryClassification.getExtractorNameProperty().get();
		this.log.info("Prepared all information.");
		
		final ImageEntry imageEntry = this.imageEntries.getSelectedImageEntry();
		final int frameSize = binaryClassification.getFrameSizeProperty().get();
		final IErrorGenerator errorGenerator = this.getErrorGenerator(errorGeneratorName);
		final IFeatureExtractor featureExtractor = this.getFeatureExtractor(featureExtractorName);
		this.log.info("Prepared all Tools.");
		
		final PredictBinaryClassificationSucceededHandler handler = new PredictBinaryClassificationSucceededHandler(); 
		final TestBinaryClassificationCommand command = this.commander.createTestBinaryClassificationCommand(predictionFolderPath, imageEntry, frameSize, errorGenerator, featureExtractor, binaryClassification);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("Initiated testing the binary classification.");
	}

	private IErrorGenerator getErrorGenerator(String errorGeneratorName) {
		for (IErrorGenerator generator : this.errorGenerators.getErrorGeneratorListProperty()) {
			if (errorGeneratorName.equals(generator.getName())) {
				return generator;
			}
		}
		
		throw new IllegalStateException("ErrorGenerator not found: " + errorGeneratorName);
	}
	
	private IFeatureExtractor getFeatureExtractor(String featureExtractorName) {
		for (IFeatureExtractor extractor : this.featureExtractors.getFeatureExtractorsProperty()) {
			if (featureExtractorName.equals(extractor.getName())) {
				return extractor;
			}
		}
		
		throw new IllegalStateException("FeatureExtractor not found: " + featureExtractorName);
	}
	
	@FXML private void onDeleteBinaryPredictionAction() {
		final BinaryPrediction selected = this.binaryPredictions.getSelectedBinaryPrediction(); 
		final DeleteBinaryPredictionSucceededHandler handler = new DeleteBinaryPredictionSucceededHandler();
		final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(selected);
		command.setOnSucceededCommandHandler(handler);
		command.start();
		this.log.info("Initiated Delete of BinaryPrediction.");
	}

	@FXML private void onResetBinaryPredictionAction() {
		final List<BinaryPrediction> binaryPredictionList = this.binaryPredictions.getBinaryPredictionListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(binaryPredictionList.size());
		
		for (final BinaryPrediction binaryPrediction : binaryPredictionList) {
			final DeleteBinaryPredictionSucceededHandler handler = new DeleteBinaryPredictionSucceededHandler();
			final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(binaryPrediction);
			command.setOnSucceededCommandHandler(handler);
			command.setExecutor(executor);
			command.start();
		}
		
		executor.execute(new Runnable() {
			@Override public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for testing the binary classification
	 * 
	 * @author Minh
	 */
	private class PredictBinaryClassificationSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final BinaryPrediction prediction = (BinaryPrediction) event.getSource().getValue();
			
			binaryPredictions.getBinaryPredictionListProperty().add(prediction);
			log.info("Added BinaryPrediction to Database.");
			
			binaryPredictions.getSelectedBinaryPredictionProperty().set(prediction);
			log.info("Set current to selected BinaryPrediction.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for testing the binary classification
	 * 
	 * @author Minh
	 */
	private class DeleteBinaryPredictionSucceededHandler extends ACommandHandler<BinaryPrediction> {
		@Override public void handle(BinaryPrediction value) {
			final List<BinaryPrediction> binaryPredictionList = binaryPredictions.getBinaryPredictionListProperty();
			
			binaryPredictionList.remove(value);
			log.info("Removed BinaryPrediction from List.");
			
			if (binaryPredictionList.size() > 0) {
				final BinaryPrediction first = binaryPredictionList.get(0);
				binaryPredictions.setSelectedBinaryPrediction(first);
				log.info("Reset to first BinaryPrediction.");
			}
		}
	}
}
