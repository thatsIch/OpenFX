package de.thatsich.bachelor.javafx.presentation.c_feature;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.InitFeatureVectorListCommand;
import de.thatsich.bachelor.javafx.business.command.CommandFactory;
import de.thatsich.bachelor.javafx.business.model.FeatureState;
import de.thatsich.bachelor.javafx.business.model.FeatureVectors;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class FeatureListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML TableView<FeatureVector> nodeTableViewFeatureVectorList;
	@FXML TableColumn<FeatureVector, String> nodeTableColumnClassName;
	@FXML TableColumn<FeatureVector, String> nodeTableColumnExtractorName;
	@FXML TableColumn<FeatureVector, Integer> nodeTableColumnFrameSize;
	@FXML TableColumn<FeatureVector, String> nodeTableColumnID;
	@FXML TableColumn<FeatureVector, String> nodeTableColumnFeatureLabel;
	@FXML TableColumn<FeatureVector, String> nodeTableColumnFeatureVector;
	
	// Injects
	@Inject private FeatureState featureState;
	@Inject private FeatureVectors featureVectors;
	@Inject private CommandFactory commander;
	@Inject private ConfigService config;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTableColums();
		this.initTableView();
		
		this.initFeatureVectorList();
	}

	private void initTableColums() {
		this.nodeTableColumnClassName.setCellValueFactory(new PropertyValueFactory<FeatureVector, String>("getClassName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<FeatureVector, String>("getExtractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<FeatureVector, Integer>("getFrameSize"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<FeatureVector, String>("getId"));
		this.nodeTableColumnFeatureLabel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FeatureVector,String>, ObservableValue<String>>() {
			@Override public ObservableValue<String> call(CellDataFeatures<FeatureVector, String> paramP) {
				return new SimpleStringProperty(paramP.getValue().getFeatureLabelProperty().get().dump());
			}
		});
		this.nodeTableColumnFeatureVector.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FeatureVector,String>, ObservableValue<String>>() {
			@Override public ObservableValue<String> call(CellDataFeatures<FeatureVector, String> paramP) {
				return new SimpleStringProperty(paramP.getValue().getFeatureVectorProperty().get().dump());
			}
		});
	}
	
	private void initTableView() {
		this.nodeTableViewFeatureVectorList.itemsProperty().bind(this.featureVectors.getFeatureVectorListProperty());
		this.log.info("Bound List to FeatureSpace.");
		
		this.nodeTableViewFeatureVectorList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FeatureVector>() {
			@Override
			public void changed(ObservableValue<? extends FeatureVector> observable, FeatureVector oldValue, FeatureVector newValue) {
				featureVectors.getSelectedFeatureVectorProperty().set(newValue);
				
				final int index = nodeTableViewFeatureVectorList.getSelectionModel().getSelectedIndex();
				config.setLastFeatureVectorIndexInt(index);
			}
		});
		
		final int index = this.config.getLastFeatureVectorIndexInt();
		this.nodeTableViewFeatureVectorList.getSelectionModel().select(index);		
	}
	
	private void initFeatureVectorList() {
		final Path folderPath = Paths.get("featurevectors");
		final InitFeatureVectorListSucceededHandler initHandler = new InitFeatureVectorListSucceededHandler();
		final GetLastFeatureVectorIndexSucceededHandler lastHandler = new GetLastFeatureVectorIndexSucceededHandler();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.featureState.getFeatureVectorFolderPathProperty().set(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");
		
		final InitFeatureVectorListCommand initCommand = this.commander.createInitFeatureVectorListCommand(folderPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized InitFeatureVectorList Retrieval.");
		
		final GetLastFeatureVectorIndexCommand lastCommand = this.commander.createGetLastFeatureVectorIndexCommand();
		lastCommand.setExecutor(executor);
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.start();
		this.log.info("Initialized GetLastFeatureVectorIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

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
	private class InitFeatureVectorListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<FeatureVector> featureVectorList = (List<FeatureVector>) event.getSource().getValue();
			
			featureVectors.getFeatureVectorListProperty().get().addAll(featureVectorList);
			log.info("Added FeatureExtractor to Database.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the LastFeatureVectorIndex
	 * 
	 * @author Minh
	 */
	private class GetLastFeatureVectorIndexSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastFeatureVectorIndex.");
			
			if (commandResult != null && commandResult >= 0 && featureVectors.getFeatureVectorListProperty().size() > commandResult) {
				final FeatureVector selectedFeatureVector = featureVectors.getFeatureVectorListProperty().get(commandResult); 
				featureVectors.getSelectedFeatureVectorProperty().set(selectedFeatureVector);
				log.info("Set LastFeatureVectorIndex in Model.");
				
				nodeTableViewFeatureVectorList.getSelectionModel().select(commandResult);
				log.info("Set LastFeatureVectorIndex in TableView.");
			}
		}
	}
}
