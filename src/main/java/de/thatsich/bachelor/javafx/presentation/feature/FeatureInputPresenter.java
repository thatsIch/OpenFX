package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
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
		this.nodeSliderFrameSize.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldBool, Boolean newBool) {
				if (newBool == false) {
					final int frameSize = (int) nodeSliderFrameSize.getValue();
					
					featureSpace.setFrameSize((int) Math.pow(2, frameSize));
					config.setLastFrameSizeInt(frameSize);
				}				
			}
		});
	
		this.log.info("Bound FrameSize to Database.");
		
		int frameSize = this.config.getLastFrameSizeInt();
		this.nodeSliderFrameSize.setValue(frameSize);
		this.featureSpace.setFrameSize(frameSize);
		this.log.info("Initialized FrameSize from Config.");
		
		// set labels to pwoer of 2
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
		final ErrorEntry errorEntry = this.errorDatabase.getSelectedErrorEntry();
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
	 * for extracting the featurevector
	 * 
	 * @author Minh
	 */
	private class ExtractSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final FeatureVector fv = (FeatureVector) event.getSource().getValue();
			
			featureSpace.getFeatureVectorListProperty().get().add(fv);
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
