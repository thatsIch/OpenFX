package de.thatsich.openfx.prediction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.INetworkPredictions;
import de.thatsich.openfx.prediction.intern.control.command.PredictionInitCommander;
import de.thatsich.openfx.prediction.intern.control.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.provider.IPredictionCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PredictionListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PredictionInitCommander init;
	@Inject private INetworkPredictions predictions;
	@Inject private IPredictionCommandProvider provider;

	// Nodes
	@FXML private TableView<INetworkPrediction> nodeTableViewBinaryPredictionList;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnDateTime;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnPredictedErrorClass;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnID;

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	@Override
	protected void initComponents()
	{

	}

	private void bindTableView()
	{
		this.bindTableViewModel();
		this.bindTableViewSelection();
		this.bindTableViewCellValue();
	}

	private void bindTableViewModel()
	{
		this.nodeTableViewBinaryPredictionList.itemsProperty().bind(this.predictions.list());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelection()
	{
		this.nodeTableViewBinaryPredictionList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldvalue, newValue) -> {
			this.predictions.selected().set(newValue);
			this.log.info("Set Selected BinaryPrediction in Model.");

			final int index = this.nodeTableViewBinaryPredictionList.getSelectionModel().getSelectedIndex();
			final SetLastBinaryPredictionIndexCommand command = this.provider.createSetLastBinaryPredictionIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.predictions.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewBinaryPredictionList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnDateTime.setCellValueFactory(cellData -> cellData.getValue().dateTime());
		this.nodeTableColumnPredictedErrorClass.setCellValueFactory(cellData -> cellData.getValue().predictedClassName());
		this.nodeTableColumnID.setCellValueFactory(cellData -> cellData.getValue().id());
	}
}
