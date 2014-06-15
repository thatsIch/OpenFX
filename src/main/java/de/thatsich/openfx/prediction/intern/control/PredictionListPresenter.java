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
import javafx.scene.control.cell.PropertyValueFactory;

public class PredictionListPresenter extends AFXMLPresenter
{

	@Inject private PredictionInitCommander initCommander;
	// Nodes
	@FXML private TableView<INetworkPrediction> nodeTableViewBinaryPredictionList;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnClassifierName;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<INetworkPrediction, Integer> nodeTableColumnFrameSize;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnErrorClassName;
	@FXML private TableColumn<INetworkPrediction, String> nodeTableColumnID;
	// Injects
	@Inject
	private INetworkPredictions binaryPredictions;
	@Inject
	private IPredictionCommandProvider provider;

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
		this.nodeTableViewBinaryPredictionList.itemsProperty().bind(this.binaryPredictions.list());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelection()
	{
		this.nodeTableViewBinaryPredictionList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldvalue, newValue) -> {
			this.binaryPredictions.selected().set(newValue);
			this.log.info("Set Selected BinaryPrediction in Model.");

			final int index = this.nodeTableViewBinaryPredictionList.getSelectionModel().getSelectedIndex();
			final SetLastBinaryPredictionIndexCommand command = this.provider.createSetLastBinaryPredictionIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.binaryPredictions.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewBinaryPredictionList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<>("getClassifierName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<>("extractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<>("tileSize"));
		this.nodeTableColumnErrorClassName.setCellValueFactory(new PropertyValueFactory<>("getErrorClassName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<>("getID"));
	}
}
