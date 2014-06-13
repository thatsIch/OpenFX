package de.thatsich.openfx.preprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessingState;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessors;
import de.thatsich.openfx.preprocessing.intern.control.command.PreProcessingInitCommander;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.CreateTrainedPreProcessorCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.RemovePreProcessingCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingCommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.handler.RemovePreProcessingSucceededHandler;
import de.thatsich.openfx.preprocessing.intern.control.handler.TrainPreProcessorSucceededHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class PreProcessingInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PreProcessingInitCommander initCommander;
	@Inject private IPreProcessingCommandProvider commander;
	@Inject private IPreProcessors preProcessors;
	@Inject private IPreProcessings preProcessings;
	@Inject private IPreProcessingState state;
	@Inject private IFeatures features;

	// Nodes
	@FXML private Button nodeButtonTrainPreProcessor;
	@FXML private Button nodeButtonRemovePreProcessing;
	@FXML private Button nodeButtonResetPreProcessingList;
	@FXML private ChoiceBox<IPreProcessor> nodeChoiceBoxPreProcessor;

	@Override
	protected void bindComponents()
	{
		this.bindChoiceBoxPreProcessors();
		this.bindButtons();
	}

	@Override
	protected void initComponents()
	{
	}

	/**
	 * Bind ChoiceBoxPreProcessors to Model.
	 */
	private void bindChoiceBoxPreProcessors()
	{
		this.nodeChoiceBoxPreProcessor.setConverter(new StringConverter<IPreProcessor>()
		{
			@Override
			public String toString(IPreProcessor pp)
			{
				return pp.getName();
			}

			@Override
			public IPreProcessor fromString(String string)
			{
				return null;
			}
		});
		this.log.info("Bound " + this.nodeChoiceBoxPreProcessor + " proper Name display.");

		this.nodeChoiceBoxPreProcessor.itemsProperty().bindBidirectional(this.preProcessors.list());
		this.nodeChoiceBoxPreProcessor.valueProperty().bindBidirectional(this.preProcessors.selected());
		this.log.info("Bound " + this.nodeChoiceBoxPreProcessor + " to Model.");

		this.nodeChoiceBoxPreProcessor.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			final SetLastPreProcessorIndexCommand lastCommand = this.commander.createSetLastPreProcessorIndexCommand(newValue.intValue());
			lastCommand.start();
		});
		this.log.info("Bound " + this.nodeChoiceBoxPreProcessor + " to Config.");

	}

	private void bindButtons()
	{
		this.nodeButtonTrainPreProcessor.disableProperty().bind(this.features.selected().isNull());
		this.nodeButtonRemovePreProcessing.disableProperty().bind(this.preProcessings.selected().isNull());
		this.nodeButtonResetPreProcessingList.disableProperty().bind(this.preProcessings.list().emptyProperty());
		this.log.info("Bound Buttons Disablility.");
	}

	// ==================================================
	// GUI Implementation
	// ==================================================
	@FXML
	private void onTrainPreProcessorAction()
	{
		final IPreProcessor selectedPreProcessor = this.preProcessors.selected().get();
		final List<IFeature> featureVectorSetList = this.features.get();
		final IFeature selectedFeatureVectorSet = this.features.selected().get();

		final CreateTrainedPreProcessorCommand command = this.commander.createTrainPreProcessorCommand(selectedPreProcessor, selectedFeatureVectorSet, featureVectorSetList);
		command.setOnSucceededCommandHandler(TrainPreProcessorSucceededHandler.class);
		command.start();
	}

	@FXML
	private void onRemovePreProcessingAction()
	{
		final ITrainedPreProcessor selected = this.preProcessings.selected().get();

		final RemovePreProcessingCommand command = this.commander.createRemovePreProcessingCommand(selected);
		command.setOnSucceededCommandHandler(RemovePreProcessingSucceededHandler.class);
		command.start();
		this.log.info("Commanded " + command);
	}

	@FXML
	private void onResetPreProcessingAction()
	{
		final List<ITrainedPreProcessor> list = this.preProcessings.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized " + executor + " for resetting.");

		for (ITrainedPreProcessor pp : list)
		{
			final RemovePreProcessingCommand command = this.commander.createRemovePreProcessingCommand(pp);
			command.setOnSucceededCommandHandler(RemovePreProcessingSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info(command + " started.");
		}

		executor.execute(System::gc);
		this.log.info("Running GC.");

		executor.shutdown();
		this.log.info("Shutting down " + executor);
	}
}
