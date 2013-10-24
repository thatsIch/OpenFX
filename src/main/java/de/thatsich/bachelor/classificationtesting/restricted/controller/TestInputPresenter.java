package de.thatsich.bachelor.classificationtesting.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.app.guice.TestCommandProvider;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.TestBinaryClassificationCommand;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.BinaryClassifications;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.restricted.models.ErrorGenerators;
import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureExtractors;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.core.javafx.AFXMLPresenter;

public class TestInputPresenter extends AFXMLPresenter {

	// Injects
	@Inject private ImageEntries imageEntries;
	@Inject private ErrorGenerators errorGenerators;
	@Inject private FeatureExtractors featureExtractors;
	@Inject private BinaryClassifications binaryClassifications;
	@Inject private BinaryPredictions binaryPredictions;
	
	@Inject private TestCommandProvider provider;
	
	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	
	/**
	 * 
	 */
	@FXML private void onTestBinaryClassifierAction() {
		final IBinaryClassification binaryClassification = this.binaryClassifications.getSelectedBinaryClassificationProperty().get();
		final String errorGeneratorName = binaryClassification.getErrorNameProperty().get();
		final String featureExtractorName = binaryClassification.getExtractorNameProperty().get();
		this.log.info("Prepared all information.");
		
		final ImageEntry imageEntry = this.imageEntries.getSelectedImageEntryProperty().get();
		final int frameSize = binaryClassification.getFrameSizeProperty().get();
		final IErrorGenerator errorGenerator = this.getErrorGenerator(errorGeneratorName);
		final IFeatureExtractor featureExtractor = this.getFeatureExtractor(featureExtractorName);
		this.log.info("Prepared all Tools.");
		
		final TestBinaryClassificationSucceededHandler handler = new TestBinaryClassificationSucceededHandler(); 
		final TestBinaryClassificationCommand command = this.provider.createTestBinaryClassificationCommand(imageEntry, frameSize, errorGenerator, featureExtractor, binaryClassification);
		command.setOnSucceeded(handler);
		command.start();
//		binaryClassification.
		// muss 
		this.log.info("");
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