package de.thatsich.bachelor.prediction.intern.control;

import java.nio.file.Path;

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
import de.thatsich.bachelor.prediction.intern.command.commands.TestBinaryClassificationCommand;
import de.thatsich.core.javafx.AFXMLPresenter;

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
	
	@Inject private BinaryPredictionCommandProvider provider;
	
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
		
		final TestBinaryClassificationSucceededHandler handler = new TestBinaryClassificationSucceededHandler(); 
		final TestBinaryClassificationCommand command = this.provider.createTestBinaryClassificationCommand(predictionFolderPath, imageEntry, frameSize, errorGenerator, featureExtractor, binaryClassification);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("Initiated testing the binary classification.");
	}
	
	// TODO maybe put them into the model?
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
	
	// TODO implement onDeleteBinaryPredictionAction
	@FXML private void onDeleteBinaryPredictionAction() {
		
	}
	
	// TODO implement onResetBinaryPredictionAction
	@FXML private void onResetBinaryPredictionAction() {
		
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for testing the binary classification
	 * 
	 * @author Minh
	 */
	private class TestBinaryClassificationSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final BinaryPrediction prediction = (BinaryPrediction) event.getSource().getValue();
			
			binaryPredictions.getBinaryPredictionListProperty().add(prediction);
			log.info("Added BinaryPrediction to Database.");
			
			binaryPredictions.getSelectedBinaryPredictionProperty().set(prediction);
			log.info("Set current to selected BinaryPrediction.");
		}
	}
}