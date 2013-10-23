package de.thatsich.bachelor.classificationtraining.restricted.controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classificationtraining.restricted.application.guice.TrainCommandProvider;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassificationListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.BinaryClassifications;
import de.thatsich.bachelor.classificationtraining.restricted.model.state.TrainState;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class TrainListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private TableView<IBinaryClassification> nodeTableViewBinaryClassificationList;
	
	@FXML private TableColumn<IBinaryClassification, String> nodeTableColumnClassifierName;
	@FXML private TableColumn<IBinaryClassification, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<IBinaryClassification, Integer> nodeTableColumnFrameSize;
	@FXML private TableColumn<IBinaryClassification, String> nodeTableErrorName;
	@FXML private TableColumn<IBinaryClassification, String> nodeTableColumnID;
	
	@Inject private BinaryClassifications binaryClassifications;
	@Inject private TrainState trainState;
	@Inject private TrainCommandProvider commander;
	
	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override public void initialize(URL location, ResourceBundle resource) {
		this.bindTableView();
		
		this.initBinaryClassificationList();
	}
	
	private void bindTableView() {
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}

	private void bindTableViewContent() {
		this.nodeTableViewBinaryClassificationList.itemsProperty().bind(this.binaryClassifications.getBinaryClassificationListProperty());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelectionModel() {
		this.nodeTableViewBinaryClassificationList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IBinaryClassification>() {
			@Override public void changed(ObservableValue<? extends IBinaryClassification> paramObservableValue, IBinaryClassification oldvalue, IBinaryClassification newValue) {
				binaryClassifications.getSelectedBinaryClassificationProperty().set(newValue);
				log.info("Set Selected BinaryClassification in Model.");
				
				final int index = nodeTableViewBinaryClassificationList.getSelectionModel().getSelectedIndex();
				final SetLastBinaryClassificationIndexCommand command = commander.createSetLastBinaryClassificationIndexCommand(index);
				command.start();
			}
		});
		this.log.info("Bound Selection to Model.");
		
		this.binaryClassifications.getSelectedBinaryClassificationProperty().addListener(new ChangeListener<IBinaryClassification>() {
			@Override public void changed(ObservableValue<? extends IBinaryClassification> observable, IBinaryClassification oldValue, IBinaryClassification newValue) {
				nodeTableViewBinaryClassificationList.getSelectionModel().select(newValue);
			}
		});
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue() {
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getClassificationName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getExtractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, Integer>("getFrameSize"));
		this.nodeTableErrorName.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getErrorName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<IBinaryClassification, String>("getId"));
	}
	
	private void initBinaryClassificationList() {
		final Path folderPath = Paths.get("io/binaryclassifier");
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.trainState.getBinaryClassifierFolderPathProperty().set(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");
		
		final InitBinaryClassificationListSucceededHandler initHandler = new InitBinaryClassificationListSucceededHandler();
		final InitBinaryClassificationListCommand initCommand = this.commander.createInitBinaryClassificationListCommand(folderPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized BinaryClassificationList Retrieval.");
		
		final GetLastBinaryClassificationIndexSucceededHandler lastHandler = new GetLastBinaryClassificationIndexSucceededHandler(); 
		final GetLastBinaryClassificationIndexCommand lastCommand = this.commander.createGetLastBinaryClassificationIndexCommand();
		lastCommand.setExecutor(executor);
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.start();
		this.log.info("Initialized LastBinaryClassificationIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the feature vector list
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitBinaryClassificationListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<IBinaryClassification> trainedBinaryClassifierList = (List<IBinaryClassification>) event.getSource().getValue();
			
			binaryClassifications.getBinaryClassificationListProperty().addAll(trainedBinaryClassifierList);
			log.info("Added TrainedBinaryClassifierList to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the LastFeatureVectorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastBinaryClassificationIndexSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastTrainedBinaryClassifierIndex.");
			
			if (commandResult != null && commandResult >= 0) {
				nodeTableViewBinaryClassificationList.getSelectionModel().select(commandResult);
				log.info("Set LastBinaryClassificationIndex in TableView.");
			}
		}
	}
}
