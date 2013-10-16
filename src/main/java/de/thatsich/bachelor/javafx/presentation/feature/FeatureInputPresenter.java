package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import de.thatsich.bachelor.javafx.business.command.RemoveFeatureVectorCommand;
import de.thatsich.bachelor.javafx.business.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.business.model.FeatureSpace;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.StringFeatureExtractorConverter;
import de.thatsich.core.opencv.IFeatureExtractor;

public class FeatureInputPresenter extends AFXMLPresenter {
	
	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private Slider nodeSliderFrameSize;

	// Injects
	@Inject private ConfigService config;
	@Inject private CommandFactory commander;
	@Inject private ErrorDatabase errorDatabase;
	@Inject private FeatureSpace featureSpace;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.bindChoiceBoxFeatureExtractor();
		this.bindSliderFrameSize();
		
		this.initFeatureExtractorList();
		this.initFeatureVectorList();
	}
	
	private void initFeatureExtractorList() {
		final InitFeatureExtractorListSucceededHandler handler = new InitFeatureExtractorListSucceededHandler();
		
		this.commander.createInitFeatureExtractorListCommand(handler).start();
		this.log.info("Initialized FeatureExtractorList Creation.");
	}
	
	private void initFeatureVectorList() {
		final Path folderPath = Paths.get("featurevectors");
		final InitFeatureVectorListSucceededHandler handler = new InitFeatureVectorListSucceededHandler();
		
		this.commander.createInitFeatureVectorListCommand(handler, folderPath).start();
		this.log.info("Initialized FeatureVectorList Creation.");
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	/**
	 * Bind ChoiceBoxFeatureExtractor to the Model.
	 */
	private void bindChoiceBoxFeatureExtractor() {
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringFeatureExtractorConverter());
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.featureSpace.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.featureSpace.getSelectedFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
		
		this.nodeChoiceBoxFeatureExtractor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				config.setLastFeatureExtractorIndexInt(newValue.intValue());
			}
		});
		this.log.info("Bound ChoiceBoxFeatureExtractor to Config.");
		
		this.nodeChoiceBoxFeatureExtractor.getSelectionModel().select(this.config.getLastFeatureExtractorIndexInt());
		this.log.info("Initialized ChoiceBoxFeatureExtractor from Config.");
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
					
					featureSpace.getFrameSizeProperty().set(result);
					config.setLastFrameSizeInt(value);
				}
			}
		});
		this.log.info("Bound FrameSize to Database.");
		
		int frameSize = this.config.getLastFrameSizeInt();
		this.nodeSliderFrameSize.setValue(frameSize);
		this.log.info("Initialized FrameSize from Config.");
		
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
		final IFeatureExtractor extractor = this.featureSpace.getSelectedFeatureExtractorProperty().get();
		final ErrorEntry errorEntry = this.errorDatabase.getSelectedErrorEntryProperty().get();
		final int frameSize = this.featureSpace.getFrameSizeProperty().get();
		this.log.info("Extracted all necessary information for a FeatureVector.");
		
		this.commander.createExtractFeatureVectorCommand(new ExtractSucceededHandler(), errorEntry, extractor, frameSize).start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onRemoveAction() {
		final FeatureVector fv = this.featureSpace.getSelectedFeatureVectorProperty().get();
		this.log.info("Fetched selected FeatureVector.");
		
		if (fv == null) {
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}
		
		this.commander.createRemoveFeatureVectorCommand(new RemoveSucceededHandler(), fv).start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}
	
	@FXML private void onResetAction() {
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(this.featureSpace.getFeatureVectorListProperty().get().size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");
		
		for (FeatureVector fv : this.featureSpace.getFeatureVectorListProperty().get()) {
			RemoveFeatureVectorCommand command = this.commander.createRemoveFeatureVectorCommand(new RemoveSucceededHandler(), fv);
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
			
			featureSpace.getFeatureExtractorsProperty().get().addAll(extractorList);
			log.info("Added FeatureExtractor to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature vector list
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitFeatureVectorListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<FeatureVector> featureVectorList = (List<FeatureVector>) event.getSource().getValue();
			
			featureSpace.getFeatureVectorListProperty().get().addAll(featureVectorList);
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
			
			featureSpace.getFeatureVectorListProperty().get().addAll(fv);
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
			
			featureSpace.getFeatureVectorListProperty().get().remove(fv);
			log.info("Removed FeatureVector from Database.");
		}
	}
}
