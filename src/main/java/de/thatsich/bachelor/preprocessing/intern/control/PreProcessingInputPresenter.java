package de.thatsich.bachelor.preprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessingState;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessings;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessors;
import de.thatsich.bachelor.preprocessing.intern.command.PreProcessingInitCommander;
import de.thatsich.bachelor.preprocessing.intern.command.commands.RemovePreProcessingCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.TrainPreProcessorCommand;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;
import de.thatsich.bachelor.preprocessing.intern.command.provider.IPreProcessingCommandProvider;
import de.thatsich.bachelor.preprocessing.intern.control.handler.RemovePreProcessingSucceededHandler;
import de.thatsich.bachelor.preprocessing.intern.control.handler.TrainPreProcessorSucceededHandler;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class PreProcessingInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject PreProcessingInitCommander initCommander;
	@Inject IPreProcessingCommandProvider commander;
	@Inject IPreProcessors preProcessors;
	@Inject IPreProcessings preProcessings;
	@Inject IPreProcessingState state;
	@Inject IFeatureVectorSets featureVectors;

	// Nodes
	@FXML Button nodeButtonTrainPreProcessor;
	@FXML Button nodeButtonRemovePreProcessing;
	@FXML Button nodeButtonResetPreProcessingList;
	@FXML ChoiceBox<IPreProcessor> nodeChoiceBoxPreProcessor;

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

		this.nodeChoiceBoxPreProcessor.itemsProperty().bindBidirectional(this.preProcessors.getPreProcessorListProperty());
		this.nodeChoiceBoxPreProcessor.valueProperty().bindBidirectional(this.preProcessors.getSelectedPreProcessorProperty());
		this.log.info("Bound " + this.nodeChoiceBoxPreProcessor + " to Model.");

		this.nodeChoiceBoxPreProcessor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				final SetLastPreProcessorIndexCommand lastCommand = commander.createSetLastPreProcessorIndexCommand(newValue.intValue());
				lastCommand.start();
			}
		});
		this.log.info("Bound " + this.nodeChoiceBoxPreProcessor + " to Config.");

	}

	private void bindButtons()
	{
		this.nodeButtonTrainPreProcessor.disableProperty().bind(this.featureVectors.getSelectedFeatureVectorSetProperty().isNull());
		this.nodeButtonRemovePreProcessing.disableProperty().bind(this.preProcessings.getSelectedPreProcessingProperty().isNull());
		this.nodeButtonResetPreProcessingList.disableProperty().bind(this.preProcessings.getPreProcessingListProperty().emptyProperty());
		this.log.info("Bound Buttons Disablility.");
	}

	// ==================================================
	// GUI Implementation
	// ==================================================
	@FXML
	private void onTrainPreProcessorAction()
	{
		final Path preProcessingFolderPath = this.state.getPreProcessingFolderPath();
		final IPreProcessor selectedPreProcessor = this.preProcessors.getSelectedPreProcessor();
		final List<FeatureVectorSet> featureVectorSetList = this.featureVectors.getFeatureVectorSetListProperty();
		final FeatureVectorSet selectedFeatureVectorSet = this.featureVectors.getSelectedFeatureVectorSet();

		final TrainPreProcessorCommand command = this.commander.createTrainPreProcessorCommand(preProcessingFolderPath, selectedPreProcessor, selectedFeatureVectorSet, featureVectorSetList);
		command.setOnSucceededCommandHandler(TrainPreProcessorSucceededHandler.class);
		command.start();
	}

	@FXML
	private void onRemovePreProcessingAction()
	{
		final IPreProcessing selected = this.preProcessings.getSelectedPreProcessing();

		final RemovePreProcessingCommand command = this.commander.createRemovePreProcessingCommand(selected);
		command.setOnSucceededCommandHandler(RemovePreProcessingSucceededHandler.class);
		command.start();
		this.log.info("Commanded " + command);
	}

	@FXML
	private void onResetPreProcessingAction()
	{
		final List<IPreProcessing> list = this.preProcessings.getPreProcessingListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized " + executor + " for resetting.");

		for (IPreProcessing pp : list)
		{
			final RemovePreProcessingCommand command = this.commander.createRemovePreProcessingCommand(pp);
			command.setOnSucceededCommandHandler(RemovePreProcessingSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info(command + " started.");
		}

		executor.execute(new Runnable()
		{
			@Override
			public void run()
			{
				System.gc();
			}
		});
		this.log.info("Running GC.");

		executor.shutdown();
		this.log.info("Shutting down " + executor);
	}
}
