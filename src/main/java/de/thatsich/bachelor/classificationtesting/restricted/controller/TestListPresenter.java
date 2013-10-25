package de.thatsich.bachelor.classificationtesting.restricted.controller;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.app.guice.TestCommandProvider;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.InitPredictionFolderCommand;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.PredictionState;
import de.thatsich.core.javafx.AFXMLPresenter;

public class TestListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private TableView<BinaryPrediction> nodeTableViewBinaryPredictionList;
	
	// Injects
	@Inject private BinaryPredictions binaryPredictions;
	@Inject private PredictionState predictionState;
	
	@Inject private TestCommandProvider provider;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		this.bindTableView();
		
		this.initInputFolder();
	}
	
	private void bindTableView() {
		this.bindTableViewModel();
		this.bindTableViewSelection();
		this.bindTableViewCellValue();
	}

	private void bindTableViewModel() {
		this.nodeTableViewBinaryPredictionList.itemsProperty().bind(this.binaryPredictions.getBinaryPredictionListProperty());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelection() {
	}

	private void bindTableViewCellValue() {
	
	}
	
	private void initInputFolder() {
		final Path predictionInputFolderPath = Paths.get("io/predictions");
		final GeInitPredictionFolderSucceededHandler handler = new GeInitPredictionFolderSucceededHandler();
		final InitPredictionFolderCommand command = this.provider.createInitPredictionFolderCommand(predictionInputFolderPath);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("Start PredictionFolder Creation.");
	}
	

	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the initialized PredictionFolder
	 * 
	 * @author Minh
	 */
	private class GeInitPredictionFolderSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Path predictionFolderPath = (Path) event.getSource().getValue();
			log.info("Retrieved PredictionFolderPath.");
			
			predictionState.getPredictionFolderPathProperty().set(predictionFolderPath);
			log.info("Set PredictionFolderPath in Model.");
		}
	}

}
