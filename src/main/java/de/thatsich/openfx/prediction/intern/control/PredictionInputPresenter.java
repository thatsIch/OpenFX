package de.thatsich.openfx.prediction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.INetworkPredictions;
import de.thatsich.openfx.prediction.api.model.IPredictionState;
import de.thatsich.openfx.prediction.intern.control.command.PredictionInitCommander;
import de.thatsich.openfx.prediction.intern.control.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.PredictNetworkCommand;
import de.thatsich.openfx.prediction.intern.control.command.handler.DeleteBinaryPredictionSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.command.handler.PredictNetworkSucceededHandler;
import de.thatsich.openfx.prediction.intern.control.provider.IPredictionCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;

public class PredictionInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PredictionInitCommander initCommander;

	@Inject private IImages imageEntries;
	@Inject private IErrorGenerators errorGenerators;
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private ITrainedClassifiers binaryClassifications;
	@Inject private IPredictionState predictionState;
	@Inject private INetworkPredictions binaryPredictions;
	@Inject private IPredictionCommandProvider commander;
	@Inject private INetworks networks;

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
		this.nodeButtonPredictBinaryClassification.disableProperty().bind(this.networks.selected().isNull());
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
		final IImage image = this.getRandomImage(this.imageEntries.list());
		final IErrorGenerator errorGenerator = this.getRandomErrorGenerator(this.errorGenerators.list());
		final ITrainedNetwork network = this.networks.selected().get();
		this.log.info("Prepared all Tools.");

		final PredictNetworkCommand command = this.commander.createPredictNetworkCommand(image, errorGenerator, network);
		command.setOnSucceededCommandHandler(PredictNetworkSucceededHandler.class);
		command.start();
		this.log.info("Initiated testing the binary classification.");
	}

	private IImage getRandomImage(List<IImage> images)
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final int randomIndex = random.nextInt(images.size());

		return images.get(randomIndex);
	}

	private IErrorGenerator getRandomErrorGenerator(List<IErrorGenerator> errorGenerators)
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final int randomIndex = random.nextInt(errorGenerators.size());

		return errorGenerators.get(randomIndex);
	}

	@FXML
	private void onDeleteBinaryPredictionAction()
	{
		final INetworkPrediction selected = this.binaryPredictions.selected().get();
		final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(selected);
		command.setOnSucceededCommandHandler(DeleteBinaryPredictionSucceededHandler.class);
		command.start();
		this.log.info("Initiated Delete of BinaryPrediction.");
	}

	@FXML
	private void onResetBinaryPredictionAction()
	{
		final List<INetworkPrediction> binaryPredictionList = this.binaryPredictions.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(binaryPredictionList.size());

		for (final INetworkPrediction binaryPrediction : binaryPredictionList)
		{
			final DeleteBinaryPredictionCommand command = this.commander.createDeleteBinaryPredictionCommand(binaryPrediction);
			command.setOnSucceededCommandHandler(DeleteBinaryPredictionSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
