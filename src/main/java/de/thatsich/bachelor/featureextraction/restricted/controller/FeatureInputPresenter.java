package de.thatsich.bachelor.featureextraction.restricted.controller;

import java.net.URL;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.models.ErrorEntries;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureExtractors;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureState;
import de.thatsich.bachelor.featureextraction.restricted.models.FeatureVectorSets;
import de.thatsich.bachelor.featureextraction.restricted.services.FeatureCommandService;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class FeatureInputPresenter extends AFXMLPresenter {
	
	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private Slider nodeSliderFrameSize;
	
	@FXML private Button nodeButtonExtractFeatureVector;
	@FXML private Button nodeButtonRemoveFeatureVector;
	@FXML private Button nodeButtonResetFeatureVectorList;

	// Injects
	@Inject private FeatureCommandService commander;
	@Inject private ErrorEntries errorEntryList;
	@Inject private FeatureExtractors featureExtractors;
	@Inject private FeatureState featureState;
	@Inject private FeatureVectorSets featureVectors;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.bindChoiceBoxFeatureExtractor();
		this.bindSliderFrameSize();
		this.bindButtons();
		
		this.initFeatureExtractorList();
		this.initFrameSize();
	}
	
	/**
	 * Gets FeatureExtractorList and preselects last selected one.
	 */
	private void initFeatureExtractorList() {
		final InitFeatureExtractorListSucceededHandler initHandler = new InitFeatureExtractorListSucceededHandler();
		final GetLastFeatureExtractorIndexSucceededHandler lastHandler = new GetLastFeatureExtractorIndexSucceededHandler(); 
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		final InitFeatureExtractorListCommand initCommand =	this.commander.createInitFeatureExtractorListCommand();
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized FeatureExtractorList Retrieval.");
		
		final GetLastFeatureExtractorIndexCommand lastCommand = this.commander.createGetLastFeatureExtractorIndexCommand();
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastFeatureExtractorIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	private void initFrameSize() {
		final GetLastFrameSizeSucceededHandler handler = new GetLastFrameSizeSucceededHandler(); 
		final GetLastFrameSizeCommand command = this.commander.createGetLastFrameSizeCommand();
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("Initialized LastFrameSize Retrieval.");
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
				final SetLastFeatureExtractorIndexCommand lastCommand = commander.createSetLastFeatureExtractorIndexCommand(newValue.intValue());
				lastCommand.start();
			}
		});
		this.log.info("Bound ChoiceBoxFeatureExtractor to Config.");
	}	
	
	/**
	 * Set the specific values of the frame size associated with each tick
	 * and sets the labelformatter to fit the representation
	 * 
	 * Java 7 has a bug with the Labels
	 */
	private void bindSliderFrameSize() {
		// write values in power of 2
		// only if slider is let loose
		this.nodeSliderFrameSize.valueProperty().addListener(new ChangeListener<Number>() {

			@Override public void changed(ObservableValue<? extends Number> paramObservableValue, Number oldValue, Number newValue) {
				if (nodeSliderFrameSize.valueChangingProperty().get() == false) {
					int result = 0;
					int value = newValue.intValue();
					
					switch(value) {
						case 2:
							result = 7; break;
						case 3:
							result = 9; break;
						case 4:
							result = 15; break;
						case 5:
							result = 31; break;
						default:
							throw new IllegalStateException("Expected numbers out of range");
					}
					
					featureState.getFrameSizeProperty().set(result);
					commander.createSetLastFrameSizeCommand(value).start();
				}
			}
		});
		this.log.info("Bound FrameSize to Database.");
		
		// set labels to pwoer of 2
		// Java 7 Bug
		this.nodeSliderFrameSize.setLabelFormatter(new StringConverter<Double>() {
			@Override public String toString(Double tick) { return String.format("%d", (int) Math.pow(2, tick)); }
			@Override public Double fromString(String paramString) { return 0.0; }
		});
		this.log.info("Formatted FrameSize.");
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
		final IFeatureExtractor extractor = this.featureExtractors.getSelectedFeatureExtractorProperty().get();
		final ErrorEntry errorEntry = this.errorEntryList.getSelectedErrorEntryProperty().get();
		final int frameSize = this.featureState.getFrameSizeProperty().get();
		final Path folderPath = this.featureState.getFeatureVectorFolderPathProperty().get();
		this.log.info("Extracted all necessary information for a FeatureVector.");
		
		if (extractor == null) throw new InvalidParameterException("Extractor is null.");
		if (errorEntry == null) throw new InvalidParameterException("ErrorEntry is null.");
		if (frameSize == 0) throw new InvalidParameterException("FrameSize is 0.");
		if (folderPath == null) throw new InvalidParameterException("FolderPath is null.");
		
		final ExtractFeatureVectorSetSucceededHandler handler = new ExtractFeatureVectorSetSucceededHandler();
		final ExtractFeatureVectorSetCommand extractCommand = this.commander.createExtractFeatureVectorCommand(folderPath, errorEntry, extractor, frameSize);
		extractCommand.setOnSucceeded(handler);
		extractCommand.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onRemoveAction() {
		final FeatureVectorSet set = this.featureVectors.getSelectedFeatureVectorSetProperty().get();
		this.log.info("Fetched selected FeatureVector.");
		
		if (set == null) {
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}
		
		final RemoveFeatureVectorSetSucceededHandler handler = new RemoveFeatureVectorSetSucceededHandler();
		final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(set);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onResetAction() {
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(this.featureVectors.getFeatureVectorSetListProperty().get().size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");
		
		for (FeatureVectorSet set : this.featureVectors.getFeatureVectorSetListProperty().get()) {
			final RemoveFeatureVectorSetSucceededHandler handler = new RemoveFeatureVectorSetSucceededHandler();
			final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(set);
			command.setOnSucceeded(handler);
			command.setExecutor(executor);
			command.start();
			this.log.info("FeatureVector Deletion executed.");
		}
		
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				System.gc();
			}
		});
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature extractor list
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitFeatureExtractorListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<IFeatureExtractor> extractorList = (List<IFeatureExtractor>) event.getSource().getValue();
			
			featureExtractors.getFeatureExtractorsProperty().get().addAll(extractorList);
			log.info("Added FeatureExtractor to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for extracting the featurevector
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class ExtractFeatureVectorSetSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<FeatureVectorSet> fv = (List<FeatureVectorSet>) event.getSource().getValue();
			
			featureVectors.getFeatureVectorSetListProperty().get().addAll(fv);
			log.info("Added FeatureVector to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for removing the featurevector
	 * 
	 * @author Minh
	 */
	private class RemoveFeatureVectorSetSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final FeatureVector fv = (FeatureVector) event.getSource().getValue();
			
			featureVectors.getFeatureVectorSetListProperty().get().remove(fv);
			log.info("Removed FeatureVector from Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting LastFeatureExtractorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastFeatureExtractorIndexSucceededHandler implements EventHandler<WorkerStateEvent> {

		@Override
		public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastFeatureExtractorIndex.");
			
			if (commandResult != null) {
				final IFeatureExtractor selectedFeatureExtractor = featureExtractors.getFeatureExtractorsProperty().get(commandResult);
				featureExtractors.getSelectedFeatureExtractorProperty().set(selectedFeatureExtractor);
				log.info("Set LastSelectedFeatureExtractor in Model.");
			}
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting LastFrameSize
	 * 
	 * @author Minh
	 */
	private class GetLastFrameSizeSucceededHandler implements EventHandler<WorkerStateEvent> {

		@Override
		public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastFrameSize.");
			
			if (commandResult != null) {
				nodeSliderFrameSize.setValue(commandResult);
				log.info("Initialized FrameSize from Config.");
			}
		}
	}
}
