package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

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

import de.thatsich.bachelor.javafx.business.command.CommandFactory;
import de.thatsich.bachelor.javafx.business.command.InitFeatureVectorListCommand;
import de.thatsich.bachelor.javafx.business.model.FeatureState;
import de.thatsich.bachelor.javafx.business.model.FeatureVectors;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.AFXMLPresenter;

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
		final InitFeatureVectorListSucceededHandler handler = new InitFeatureVectorListSucceededHandler();
		
		this.featureState.getFeatureVectorFolderPathProperty().set(folderPath);
		this.log.info("Set FeatureVectorInputFolderPath to Model.");
		
		final InitFeatureVectorListCommand command = this.commander.createInitFeatureVectorListCommand(folderPath);
		command.setOnSucceeded(handler);
		command.start();
		this.log.info("Initialized FeatureVectorList Creation.");
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
}
