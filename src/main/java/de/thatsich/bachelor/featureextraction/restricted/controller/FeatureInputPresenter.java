package de.thatsich.bachelor.featureextraction.restricted.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.command.IFeatureCommandProvider;
import de.thatsich.bachelor.featureextraction.restricted.command.FeatureInitCommander;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.controller.handler.ExtractFeatureVectorSetSucceededHandler;
import de.thatsich.bachelor.featureextraction.restricted.controller.handler.RemoveFeatureVectorSetSucceededHandler;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;

public class FeatureInputPresenter extends AFXMLPresenter {
	
	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private IntegerField nodeIntegerFieldFrameSize;
	
	@FXML private Button nodeButtonExtractFeatureVector;
	@FXML private Button nodeButtonRemoveFeatureVector;
	@FXML private Button nodeButtonResetFeatureVectorList;

	// Injects
	@Inject FeatureInitCommander initCommander;
	@Inject private IFeatureCommandProvider commander;
	@Inject private IErrorEntries errorEntryList;
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private IFeatureState featureState;
	@Inject private IFeatureVectorSets featureVectors;
	
	@Override
	protected void initComponents() {
	}

	@Override
	protected void bindComponents() {
		this.bindChoiceBoxFeatureExtractor();
		this.bindIntegerFieldFrameSize();
		this.bindButtons();
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	/**
	 * Bind ChoiceBoxFeatureExtractor to the Model.
	 */
	private void bindChoiceBoxFeatureExtractor() {
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringConverter<IFeatureExtractor>() {
			@Override public IFeatureExtractor fromString(String arg0) { return null; }
			@Override public String toString(IFeatureExtractor featureGenerator) { return featureGenerator.getName(); }
		});
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.featureExtractors.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.featureExtractors.getSelectedFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
		
		this.nodeChoiceBoxFeatureExtractor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				commander.createSetLastFeatureExtractorIndexCommand(newValue.intValue()).start();
			}
		});
		this.log.info("Bound ChoiceBoxFeatureExtractor to Config.");
	}	
	
	/**
	 * 
	 */
	private void bindIntegerFieldFrameSize() {
		this.nodeIntegerFieldFrameSize.valueProperty().bindBidirectional(this.featureState.getFrameSizeProperty());
		this.log.info("Bound FrameSize to DataBase");

		this.nodeIntegerFieldFrameSize.valueProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> paramObservableValue, Number oldValue, Number newValue) {
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
	private void bindButtons() {
		this.nodeButtonExtractFeatureVector.disableProperty().bind(this.errorEntryList.getSelectedErrorEntryProperty().isNull().or(this.nodeChoiceBoxFeatureExtractor.valueProperty().isNull()));
		this.nodeButtonRemoveFeatureVector.disableProperty().bind(this.featureVectors.getSelectedFeatureVectorSetProperty().isNull());
		this.nodeButtonResetFeatureVectorList.disableProperty().bind(this.featureVectors.getFeatureVectorSetListProperty().emptyProperty());
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	@FXML private void onExtractAction() {
		final ExtractFeatureVectorSetCommand extractCommand = this.commander.createExtractFeatureVectorCommand(
				this.featureState.getFeatureVectorFolderPath(), 
				this.errorEntryList.getSelectedErrorEntry(), 
				this.featureExtractors.getSelectedFeatureExtractor(), 
				this.featureState.getFrameSize());
		extractCommand.setOnSucceededCommandHandler(ExtractFeatureVectorSetSucceededHandler.class);
		extractCommand.setOnSucceeded(new ExtractFeatureVectorSetSucceededHandler());
		extractCommand.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onRemoveAction() {
		final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(this.featureVectors.getSelectedFeatureVectorSet());
		command.setOnSucceededCommandHandler(RemoveFeatureVectorSetSucceededHandler.class);
		command.start();
		this.log.info("FeatureVectorSet deletion instantiated.");
	}
	
	@FXML private void onResetAction() {
		final List<FeatureVectorSet> list = this.featureVectors.getFeatureVectorSetListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");
		
		for (FeatureVectorSet set : list) {
			final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(set);
			command.setOnSucceededCommandHandler(RemoveFeatureVectorSetSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("FeatureVector Deletion executed.");
		}
		
		executor.execute(new Runnable() { @Override public void run() { System.gc(); } });
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
