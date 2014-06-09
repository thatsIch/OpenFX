package de.thatsich.openfx.prediction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import de.thatsich.openfx.prediction.api.model.IPredictionState;
import de.thatsich.openfx.prediction.intern.control.command.PredictionInitCommander;
import de.thatsich.openfx.prediction.intern.control.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.TestBinaryClassificationCommand;
import de.thatsich.openfx.prediction.intern.control.command.handler.DeleteBinaryPredictionSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.command.handler.PredictBinaryClassificationSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.entity.BinaryPrediction;
import de.thatsich.openfx.prediction.intern.control.provider.IPredictionCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class PredictionInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PredictionInitCommander initCommander;

	@Inject private IImages imageEntries;
	@Inject private IErrorGenerators errorGenerators;
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private IBinaryClassifications binaryClassifications;
	@Inject private IPredictionState predictionState;
	@Inject private IBinaryPredictions binaryPredictions;
	@Inject private IPredictionCommandProvider commander;

	// Nodes
	@FXML private Button nodeButtonPredictBinaryClassification;
	@FXML private Button nodeButtonDeleteBinaryPrediction;
	@FXML private Button nodeButtonResetBinaryPrediction;

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
		this.nodeButtonPredictBinaryClassification.disableProperty().bind(this.imageEntries.selected().isNull().or(this.binaryClassifications.selected().isNull()));
		this.nodeButtonDeleteBinaryPrediction.disableProperty().bind(this.binaryPredictions.selected().isNull());
		this.nodeButtonResetBinaryPrediction.disableProperty().bind(this.binaryPredictions.list().emptyProperty());
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
		final Path predictionFolderPath = this.predictionState.path().get();
		final IBinaryClassification binaryClassification = this.binaryClassifications.selected().get();
		final String errorGeneratorName = binaryClassification.errorNameProperty().get();
		final String featureExtractorName = binaryClassification.extractorNameProperty().get();
		this.log.info("Prepared all information.");

		final IImage imageEntry = this.imageEntries.selected().get();
		final int frameSize = binaryClassification.tileSizeProperty().get();
		final IErrorGenerator errorGenerator = this.getErrorGenerator(errorGeneratorName);
		final IFeatureExtractor featureExtractor = this.getFeatureExtractor(featureExtractorName);
		this.log.info("Prepared all Tools.");

		final TestBinaryClassificationCommand command = this.commander.createTestBinaryClassificationCommand(imageEntry, frameSize, errorGenerator, featureExtractor, binaryClassification);
		command.setOnSucceededCommandHandler(PredictBinaryClassificationSucceededHandler.class);
		command.start();
		this.log.info("Initiated testing the binary classification.");
	}

	private IErrorGenerator getErrorGenerator(String errorGeneratorName)
	{
		for (IErrorGenerator generator : this.errorGenerators.list())
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
		for (IFeatureExtractor extractor : this.featureExtractors.list())
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
		final BinaryPrediction selected = this.binaryPredictions.selected().get();
		final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(selected);
		command.setOnSucceededCommandHandler(DeleteBinaryPredictionSucceededHandler.class);
		command.start();
		this.log.info("Initiated Delete of BinaryPrediction.");
	}

	@FXML
	private void onResetBinaryPredictionAction()
	{
		final List<BinaryPrediction> binaryPredictionList = this.binaryPredictions.list();
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
