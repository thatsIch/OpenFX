package de.thatsich.bachelor.prediction.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classification.api.models.IBinaryClassifications;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.core.IPredictionState;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.command.PredictionInitCommander;
import de.thatsich.bachelor.prediction.intern.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.TestBinaryClassificationCommand;
import de.thatsich.bachelor.prediction.intern.command.provider.IPredictionCommandProvider;
import de.thatsich.bachelor.prediction.intern.control.handler.DeleteBinaryPredictionSucceededHandler;
import de.thatsich.bachelor.prediction.intern.control.handler.PredictBinaryClassificationSucceededHandler;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class PredictionInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject PredictionInitCommander initCommander;
	@Inject IImageEntries imageEntries;
	@Inject IErrorGenerators errorGenerators;
	@Inject IFeatureExtractors featureExtractors;
	@Inject IBinaryClassifications binaryClassifications;
	@Inject IPredictionState predictionState;
	@Inject IBinaryPredictions binaryPredictions;
	@Inject IPredictionCommandProvider commander;

	// Nodes
	@FXML Button nodeButtonPredictBinaryClassification;
	@FXML Button nodeButtonDeleteBinaryPrediction;
	@FXML Button nodeButtonResetBinaryPrediction;

	@Override
	protected void bindComponents()
	{
		this.bindButtons();
	}

	// ==================================================
	// Initialization Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	private void bindButtons()
	{
		this.nodeButtonPredictBinaryClassification.disableProperty().bind(this.imageEntries.selectedImageEntryProperty().isNull().or(this.binaryClassifications.getSelectedBinaryClassificationProperty().isNull()));
		this.nodeButtonDeleteBinaryPrediction.disableProperty().bind(this.binaryPredictions.getSelectedBinaryPredictionProperty().isNull());
		this.nodeButtonResetBinaryPrediction.disableProperty().bind(this.binaryPredictions.getBinaryPredictionListProperty().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================

	/**
	 * predict
	 */
	@FXML
	private void onPredictBinaryPredictionAction()
	{
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

		final TestBinaryClassificationCommand command = this.commander.createTestBinaryClassificationCommand(predictionFolderPath, imageEntry, frameSize, errorGenerator, featureExtractor, binaryClassification);
		command.setOnSucceededCommandHandler(PredictBinaryClassificationSucceededHandler.class);
		command.start();
		this.log.info("Initiated testing the binary classification.");
	}

	private IErrorGenerator getErrorGenerator(String errorGeneratorName)
	{
		for (IErrorGenerator generator : this.errorGenerators.getErrorGeneratorListProperty())
		{
			if (errorGeneratorName.equals(generator.getName()))
			{
				return generator;
			}
		}

		throw new IllegalStateException("ErrorGenerator not found: " + errorGeneratorName);
	}

	private IFeatureExtractor getFeatureExtractor(String featureExtractorName)
	{
		for (IFeatureExtractor extractor : this.featureExtractors.getFeatureExtractorsProperty())
		{
			if (featureExtractorName.equals(extractor.getName()))
			{
				return extractor;
			}
		}

		throw new IllegalStateException("FeatureExtractor not found: " + featureExtractorName);
	}

	@FXML
	private void onDeleteBinaryPredictionAction()
	{
		final BinaryPrediction selected = this.binaryPredictions.getSelectedBinaryPrediction();
		final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(selected);
		command.setOnSucceededCommandHandler(DeleteBinaryPredictionSucceededHandler.class);
		command.start();
		this.log.info("Initiated Delete of BinaryPrediction.");
	}

	@FXML
	private void onResetBinaryPredictionAction()
	{
		final List<BinaryPrediction> binaryPredictionList = this.binaryPredictions.getBinaryPredictionListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(binaryPredictionList.size());

		for (final BinaryPrediction binaryPrediction : binaryPredictionList)
		{
			final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(binaryPrediction);
			command.setOnSucceededCommandHandler(DeleteBinaryPredictionSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}

		executor.execute(new Runnable()
		{
			@Override
			public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
