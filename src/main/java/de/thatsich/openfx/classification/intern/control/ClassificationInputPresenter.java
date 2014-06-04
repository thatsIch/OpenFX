package de.thatsich.openfx.classification.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;
import de.thatsich.openfx.classification.api.model.IClassificationState;
import de.thatsich.openfx.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.ClassificationInitCommander;
import de.thatsich.openfx.classification.intern.control.command.commands.RemoveBinaryClassificationCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.TrainBinaryClassifierCommand;
import de.thatsich.openfx.classification.intern.control.handler.RemoveBinaryClassificationSucceededHandler;
import de.thatsich.openfx.classification.intern.control.handler.TrainBinaryClassifierSucceededHandler;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ClassificationInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ClassificationInitCommander initCommander;
	@Inject private IClassificationCommandProvider commander;
	@Inject private IBinaryClassifiers binaryClassifiers;
	@Inject private IBinaryClassifications binaryClassifications;
	@Inject private IClassificationState trainState;
	@Inject private IFeatures features;

	// Nodes
	@FXML private ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	@FXML private Button nodeButtonTrainBinaryClassifier;
	@FXML private Button nodeButtonRemoveBinaryClassifier;
	@FXML private Button nodeButtonResetBinaryClassifierList;

	@Override
	protected void bindComponents()
	{
		this.bindChoiceBoxBinaryClassifier();
		this.bindButtons();
	}

	// ==================================================
	// Initialization Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{

	}

	/**
	 * Bind ChoiceBoxBinaryClassifier to the Model.
	 */
	private void bindChoiceBoxBinaryClassifier()
	{
		this.nodeChoiceBoxBinaryClassifier.setConverter(new StringConverter<IBinaryClassifier>()
		{
			@Override
			public String toString(IBinaryClassifier bc)
			{
				return bc.getName();
			}

			@Override
			public IBinaryClassifier fromString(String string)
			{
				return null;
			}
		});
		this.log.info("Set up ChoiceBoxBinaryClassifier for proper name display.");

		this.nodeChoiceBoxBinaryClassifier.itemsProperty().bindBidirectional(this.binaryClassifiers.binaryClassifiers());
		this.nodeChoiceBoxBinaryClassifier.valueProperty().bindBidirectional(this.binaryClassifiers.selectedBinaryClassifier());
		this.log.info("Bound ChoiceBoxBinaryClassifier to Model.");

		this.nodeChoiceBoxBinaryClassifier.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			final SetLastBinaryClassifierIndexCommand lastCommand = this.commander.createSetLastBinaryClassifierIndexCommand(newValue.intValue());
			lastCommand.start();
		});
		this.log.info("Bound ChoiceBoxBinaryClassifier to Config.");
	}

	private void bindButtons()
	{
		this.nodeButtonTrainBinaryClassifier.disableProperty().bind(this.features.list().isNull());
		this.nodeButtonRemoveBinaryClassifier.disableProperty().bind(this.binaryClassifications.selectedBinaryClassification().isNull());
		this.nodeButtonResetBinaryClassifierList.disableProperty().bind(this.binaryClassifications.binaryClassifications().emptyProperty());
	}

	// ==================================================
	// GUI Implementation
	// ==================================================
	@FXML
	private void onTrainBinaryClassifierAction()
	{
		final Path binaryClassifierFolderPath = this.trainState.path().get();
		final IBinaryClassifier selectedBinaryClassfier = this.binaryClassifiers.selectedBinaryClassifier().get();
		final List<IFeature> features = this.features.list();
		final IFeature selected = this.features.selectedFeature().get();

		final TrainBinaryClassifierCommand command = this.commander.createTrainBinaryClassifierCommand(binaryClassifierFolderPath, selectedBinaryClassfier, selected, features);
		command.setOnSucceededCommandHandler(TrainBinaryClassifierSucceededHandler.class);
		command.start();
	}

	@FXML
	private void onRemoveBinaryClassifierAction()
	{
		final IBinaryClassification selectedBinaryClassification = this.binaryClassifications.selectedBinaryClassification().get();

		final RemoveBinaryClassificationCommand command = this.commander.createRemoveBinaryClassificationCommand(selectedBinaryClassification);
		command.setOnSucceededCommandHandler(RemoveBinaryClassificationSucceededHandler.class);
		command.start();
		this.log.info("Commanded BinaryClassification Removal.");
	}

	@FXML
	private void onResetBinaryClassifierListAction()
	{
		final List<IBinaryClassification> binaryClassificationList = this.binaryClassifications.binaryClassifications();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(binaryClassificationList.size());
		this.log.info("Initialized Executor for resetting all Errors.");

		for (IBinaryClassification classification : binaryClassificationList)
		{
			final RemoveBinaryClassificationCommand command = this.commander.createRemoveBinaryClassificationCommand(classification);
			command.setOnSucceededCommandHandler(RemoveBinaryClassificationSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("File Deletion executed.");
		}

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
