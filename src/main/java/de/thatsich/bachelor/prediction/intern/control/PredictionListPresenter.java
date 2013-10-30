package de.thatsich.bachelor.prediction.intern.control;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.command.BinaryPredictionCommandProvider;
import de.thatsich.bachelor.prediction.intern.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitBinaryPredictionListCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitPredictionFolderCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.model.BinaryPredictions;
import de.thatsich.bachelor.prediction.intern.model.PredictionState;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class PredictionListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private TableView<BinaryPrediction> nodeTableViewBinaryPredictionList;
	
	// Injects
	@Inject private BinaryPredictions binaryPredictions;
	@Inject private PredictionState predictionState;
	
	@Inject private BinaryPredictionCommandProvider provider;
	
	@Override
	protected void initComponents() {
		this.initBinaryPredictionList();
	}

	@Override
	protected void bindComponents() {
		this.bindTableView();
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
		this.nodeTableViewBinaryPredictionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BinaryPrediction>() {
			@Override public void changed(ObservableValue<? extends BinaryPrediction> paramObservableValue, BinaryPrediction oldvalue, BinaryPrediction newValue) {
				binaryPredictions.getSelectedBinaryPredictionProperty().set(newValue);
				log.info("Set Selected BinaryPrediction in Model.");
				
				final int index = nodeTableViewBinaryPredictionList.getSelectionModel().getSelectedIndex();
				final SetLastBinaryPredictionIndexCommand command = provider.createSetLastBinaryPredictionIndexCommand(index);
				command.start();
			}
		});
		this.log.info("Bound Selection to Model.");
		
		this.binaryPredictions.getSelectedBinaryPredictionProperty().addListener(new ChangeListener<BinaryPrediction>() {

			@Override
			public void changed(
					ObservableValue<? extends BinaryPrediction> observable,
					BinaryPrediction oldValue, BinaryPrediction newValue) {
				
				nodeTableViewBinaryPredictionList.getSelectionModel().select(newValue);
				
			}
			
		});
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue() {
//		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getClassificationName"));
//		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getExtractorName"));
//		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, Integer>("getFrameSize"));
//		this.nodeTableErrorName.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getErrorName"));
//		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getId"));
	}
		
	private void initBinaryPredictionList() {
		final Path predictionInputFolderPath = Paths.get("io/predictions");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		this.log.info("Prepared BinaryPrediction Preparations.");
		
		this.predictionState.getPredictionFolderPathProperty().set(predictionInputFolderPath);
		this.log.info("Set BinaryPredictionFolderPath in Model.");
		
		final InitPredictionFolderCommand command = this.provider.createInitPredictionFolderCommand(predictionInputFolderPath);
		command.setExecutor(executor);
		command.start();
		this.log.info("Start PredictionFolder Creation.");
		
		final InitBinaryPredictionListSucceededHandler initHandler = new InitBinaryPredictionListSucceededHandler();
		final InitBinaryPredictionListCommand initCommand = this.provider.createInitBinaryPredictionListCommand(predictionInputFolderPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryPredictionList Retrieval.");
		
		final GetLastBinaryPredictionIndexSucceededHandler lastHandler = new GetLastBinaryPredictionIndexSucceededHandler();
		final GetLastBinaryPredictionIndexCommand lastCommand = this.provider.createGetLastBinaryPredictionIndexCommand();
		lastCommand.setExecutor(executor);
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.start();
		this.log.info("Initialized LastBinaryPredictionIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature vector list
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitBinaryPredictionListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<BinaryPrediction> binaryPredictionList = (List<BinaryPrediction>) event.getSource().getValue();
			
			binaryPredictions.getBinaryPredictionListProperty().addAll(binaryPredictionList);
			log.info("Added BinaryPredictionList to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the LastFeatureVectorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastBinaryPredictionIndexSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastBinaryPredictionIndex.");
			
			if (commandResult != null && commandResult >= 0) {
				nodeTableViewBinaryPredictionList.getSelectionModel().select(commandResult);
				log.info("Set LastBinaryPredictionIndex in TableView.");
			}
		}
	}
}
