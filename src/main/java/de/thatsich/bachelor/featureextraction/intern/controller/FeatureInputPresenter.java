package de.thatsich.bachelor.featureextraction.intern.controller;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.intern.command.FeatureInitCommander;
import de.thatsich.bachelor.featureextraction.intern.command.IFeatureCommandProvider;
import de.thatsich.bachelor.featureextraction.intern.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.intern.controller.handler.ExtractFeatureVectorSetSucceededHandler;
import de.thatsich.bachelor.featureextraction.intern.controller.handler.RemoveFeatureVectorSetSucceededHandler;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class FeatureInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject FeatureInitCommander initCommander;
	@Inject IFeatureCommandProvider commander;
	@Inject IErrorEntries errorEntryList;
	@Inject IFeatureExtractors featureExtractors;
	@Inject IFeatureState featureState;
	@Inject IFeatureVectorSets featureVectors;

	// Nodes
	@FXML ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML IntegerField nodeIntegerFieldFrameSize;
	@FXML Button nodeButtonExtractFeatureVector;
	@FXML Button nodeButtonRemoveFeatureVector;
	@FXML Button nodeButtonResetFeatureVectorList;
	@FXML CheckBox nodeCheckBoxSmooth;
	@FXML CheckBox nodeCheckBoxThreshold;
	@FXML CheckBox nodeCheckBoxDenoising;

	@Override
	protected void bindComponents()
	{
		this.bindChoiceBoxFeatureExtractor();
		this.bindIntegerFieldFrameSize();
		this.bindButtons();
	}

	@Override
	protected void initComponents()
	{
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================

	/**
	 * Bind ChoiceBoxFeatureExtractor to the Model.
	 */
	private void bindChoiceBoxFeatureExtractor()
	{
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringConverter<IFeatureExtractor>()
		{
			@Override
			public String toString(IFeatureExtractor featureGenerator) { return featureGenerator.getName(); }

			@Override
			public IFeatureExtractor fromString(String arg0) { return null; }
		});
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");

		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.featureExtractors.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.featureExtractors.getSelectedFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");

		this.nodeChoiceBoxFeatureExtractor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				commander.createSetLastFeatureExtractorIndexCommand(newValue.intValue()).start();
			}
		});
		this.log.info("Bound ChoiceBoxFeatureExtractor to Config.");
	}

	/**
	 *
	 */
	private void bindIntegerFieldFrameSize()
	{
		this.nodeIntegerFieldFrameSize.valueProperty().bindBidirectional(this.featureState.getFrameSizeProperty());
		this.log.info("Bound FrameSize to DataBase");

		this.nodeIntegerFieldFrameSize.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> paramObservableValue, Number oldValue, Number newValue)
			{
				final int frameSize = newValue.intValue();

				featureState.setFrameSize(frameSize);
				commander.createSetLastFrameSizeCommand(frameSize).start();
				log.info("Initiated SetLastFrameSizeCommand with " + frameSize + ".");
			}
		});
		this.log.info("Bound FrameSize to Config.");
	}

	/**
	 * Simple Validator Binding for Buttons
	 */
	private void bindButtons()
	{
		this.nodeButtonExtractFeatureVector.disableProperty().bind(this.errorEntryList.getSelectedErrorEntryProperty().isNull().or(this.nodeChoiceBoxFeatureExtractor.valueProperty().isNull()));
		this.nodeButtonRemoveFeatureVector.disableProperty().bind(this.featureVectors.getSelectedFeatureVectorSetProperty().isNull());
		this.nodeButtonResetFeatureVectorList.disableProperty().bind(this.featureVectors.getFeatureVectorSetListProperty().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================
	@FXML
	private void onExtractAction()
	{
		final ExtractFeatureVectorSetCommand extractCommand = this.commander.createExtractFeatureVectorCommand(this.featureState.getFeatureVectorFolderPath(), this.errorEntryList.getSelectedErrorEntry(), this.featureExtractors.getSelectedFeatureExtractor(), this.featureState.getFrameSize(), this.nodeCheckBoxSmooth.isSelected(), this.nodeCheckBoxThreshold.isSelected(), this.nodeCheckBoxDenoising.isSelected());
		extractCommand.setOnSucceededCommandHandler(ExtractFeatureVectorSetSucceededHandler.class);
		extractCommand.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}

	@FXML
	private void onRemoveAction()
	{
		final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(this.featureVectors.getSelectedFeatureVectorSet());
		command.setOnSucceededCommandHandler(RemoveFeatureVectorSetSucceededHandler.class);
		command.start();
		this.log.info("FeatureVectorSet deletion instantiated.");
	}

	@FXML
	private void onResetAction()
	{
		final List<FeatureVectorSet> list = this.featureVectors.getFeatureVectorSetListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");

		for (FeatureVectorSet set : list)
		{
			final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(set);
			command.setOnSucceededCommandHandler(RemoveFeatureVectorSetSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("FeatureVector Deletion executed.");
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
