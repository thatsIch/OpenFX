package de.thatsich.bachelor.classificationtesting.restricted.controller;

import java.nio.file.Path;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.model.BinaryClassifications;
import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.app.guice.BinaryPredictionCommandProvider;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.TestBinaryClassificationCommand;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.PredictionState;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.restricted.model.ErrorGenerators;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureExtractors;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.core.javafx.AFXMLPresenter;

public class TestInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private Button nodeButtonTestBinaryClassification;
	
	// Injects
	@Inject private ImageEntries imageEntries;
	@Inject private ErrorGenerators errorGenerators;
	@Inject private FeatureExtractors featureExtractors;
	@Inject private BinaryClassifications binaryClassifications;
	@Inject private PredictionState predictionState;
	@Inject private BinaryPredictions binaryPredictions;
	
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
		this.nodeButtonTestBinaryClassification.disableProperty().bind(this.imageEntries.selectedImageEntryProperty().isNull().or(this.binaryClassifications.getSelectedBinaryClassificationProperty().isNull()));
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	
	/**
	 * 
	 */
	@FXML private void onTestBinaryClassifierAction() {
		final Path predictionFolderPath = this.predictionState.getPredictionFolderPathProperty().get();
		final IBinaryClassification binaryClassification = this.binaryClassifications.getSelectedBinaryClassificationProperty().get();
		final String errorGeneratorName = binaryClassification.getErrorNameProperty().get();
		final String featureExtractorName = binaryClassification.getExtractorNameProperty().get();
		this.log.info("Prepared all information.");
		
		final ImageEntry imageEntry = this.imageEntries.selectedImageEntryProperty().get();
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
