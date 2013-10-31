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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.core.IPredictionState;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.command.BinaryPredictionCommandProvider;
import de.thatsich.bachelor.prediction.intern.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitBinaryPredictionListCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitPredictionFolderCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class PredictionListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private TableView<BinaryPrediction> nodeTableViewBinaryPredictionList;
	@FXML private TableColumn<BinaryPrediction, String> nodeTableColumnClassifierName;
	@FXML private TableColumn<BinaryPrediction, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<BinaryPrediction, Integer> nodeTableColumnFrameSize;
	@FXML private TableColumn<BinaryPrediction, String> nodeTableColumnErrorClassName;
	@FXML private TableColumn<BinaryPrediction, String> nodeTableColumnID;
	
	// Injects
	@Inject private IBinaryPredictions binaryPredictions;
	@Inject private IPredictionState predictionState;
	
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
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getClassifierName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getExtractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, Integer>("getFrameSize"));
		this.nodeTableColumnErrorClassName.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getErrorClassName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getID"));
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
