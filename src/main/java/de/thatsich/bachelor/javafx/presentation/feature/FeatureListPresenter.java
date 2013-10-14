package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.FeatureSpace;
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
	@Inject private FeatureSpace featureSpace;
	@Inject private ConfigService config;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTableColums();
		this.initTableView();
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
		this.nodeTableViewFeatureVectorList.itemsProperty().bind(this.featureSpace.getFeatureVectorListProperty());
		this.log.info("Bound List to FeatureSpace.");
		
		this.nodeTableViewFeatureVectorList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FeatureVector>() {
			@Override
			public void changed(ObservableValue<? extends FeatureVector> observable, FeatureVector oldValue, FeatureVector newValue) {
				featureSpace.getSelectedFeatureVectorProperty().set(newValue);
				
				final int index = nodeTableViewFeatureVectorList.getSelectionModel().getSelectedIndex();
				config.setLastFeatureVectorIndexInt(index);
			}
		});
		
		final int index = this.config.getLastFeatureVectorIndexInt();
		this.nodeTableViewFeatureVectorList.getSelectionModel().select(index);		
	}
}
