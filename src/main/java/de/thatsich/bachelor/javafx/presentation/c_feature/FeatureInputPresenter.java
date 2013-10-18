package de.thatsich.bachelor.javafx.presentation.c_feature;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.command.CommandFactory;
import de.thatsich.bachelor.javafx.business.command.DeleteFeatureVectorCommand;
import de.thatsich.bachelor.javafx.business.command.ExtractFeatureVectorFromErrorEntryCommand;
import de.thatsich.bachelor.javafx.business.command.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.javafx.business.command.GetLastFrameSizeCommand;
import de.thatsich.bachelor.javafx.business.command.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.javafx.business.command.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.javafx.business.model.ErrorEntries;
import de.thatsich.bachelor.javafx.business.model.FeatureExtractors;
import de.thatsich.bachelor.javafx.business.model.FeatureState;
import de.thatsich.bachelor.javafx.business.model.FeatureVectors;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.opencv.IFeatureExtractor;

public class FeatureInputPresenter extends AFXMLPresenter {
	
	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private Slider nodeSliderFrameSize;

	// Injects
//	@Inject private ConfigService config;
	@Inject private CommandFactory commander;
	@Inject private ErrorEntries errorEntryList;
	@Inject private FeatureExtractors featureExtractors;
	@Inject private FeatureState featureState;
	@Inject private FeatureVectors featureVectors;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.bindChoiceBoxFeatureExtractor();
		this.bindSliderFrameSize();
		
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
		
		final ExtractSucceededHandler handler = new ExtractSucceededHandler();
		final ExtractFeatureVectorFromErrorEntryCommand extractCommand = this.commander.createExtractFeatureVectorCommand(folderPath, errorEntry, extractor, frameSize);
		extractCommand.setOnSucceeded(handler);
		extractCommand.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onRemoveAction() {
		final FeatureVector fv = this.featureVectors.getSelectedFeatureVectorProperty().get();
		this.log.info("Fetched selected FeatureVector.");
		
		if (fv == null) {
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}
		
		final RemoveSucceededHandler handler = new RemoveSucceededHandler();
		final DeleteFeatureVectorCommand command = this.commander.createRemoveFeatureVectorCommand(fv);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onResetAction() {
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(this.featureVectors.getFeatureVectorListProperty().get().size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");
		
		for (FeatureVector fv : this.featureVectors.getFeatureVectorListProperty().get()) {
			final RemoveSucceededHandler handler = new RemoveSucceededHandler();
			final DeleteFeatureVectorCommand command = this.commander.createRemoveFeatureVectorCommand(fv);
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
	private class ExtractSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<FeatureVector> fv = (List<FeatureVector>) event.getSource().getValue();
			
			featureVectors.getFeatureVectorListProperty().get().addAll(fv);
			log.info("Added FeatureVector to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for removing the featurevector
	 * 
	 * @author Minh
	 */
	private class RemoveSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final FeatureVector fv = (FeatureVector) event.getSource().getValue();
			
			featureVectors.getFeatureVectorListProperty().get().remove(fv);
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