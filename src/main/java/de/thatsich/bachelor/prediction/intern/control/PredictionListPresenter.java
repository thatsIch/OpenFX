package de.thatsich.bachelor.prediction.intern.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.command.PredictionInitCommander;
import de.thatsich.bachelor.prediction.intern.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.command.provider.IPredictionCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;

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
	
	@Inject private IPredictionCommandProvider provider;
	
	@Inject PredictionInitCommander initCommander;
	
	@Override
	protected void initComponents() {
		
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
}
