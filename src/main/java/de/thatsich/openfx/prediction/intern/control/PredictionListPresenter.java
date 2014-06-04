package de.thatsich.openfx.prediction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import de.thatsich.openfx.prediction.api.model.IBinaryPredictions;
import de.thatsich.openfx.prediction.intern.control.command.PredictionInitCommander;
import de.thatsich.openfx.prediction.intern.control.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.provider.IPredictionCommandProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PredictionListPresenter extends AFXMLPresenter
{

	@Inject PredictionInitCommander initCommander;
	// Nodes
	@FXML TableView<BinaryPrediction> nodeTableViewBinaryPredictionList;
	@FXML TableColumn<BinaryPrediction, String> nodeTableColumnClassifierName;
	@FXML TableColumn<BinaryPrediction, String> nodeTableColumnExtractorName;
	@FXML TableColumn<BinaryPrediction, Integer> nodeTableColumnFrameSize;
	@FXML TableColumn<BinaryPrediction, String> nodeTableColumnErrorClassName;
	@FXML TableColumn<BinaryPrediction, String> nodeTableColumnID;
	// Injects
	@Inject
	private IBinaryPredictions binaryPredictions;
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
		this.nodeTableViewBinaryPredictionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BinaryPrediction>()
		{
			@Override
			public void changed(ObservableValue<? extends BinaryPrediction> paramObservableValue, BinaryPrediction oldvalue, BinaryPrediction newValue)
			{
				binaryPredictions.selected().set(newValue);
				log.info("Set Selected BinaryPrediction in Model.");

				final int index = nodeTableViewBinaryPredictionList.getSelectionModel().getSelectedIndex();
				final SetLastBinaryPredictionIndexCommand command = provider.createSetLastBinaryPredictionIndexCommand(index);
				command.start();
			}
		});
		this.log.info("Bound Selection to Model.");

		this.binaryPredictions.selected().addListener(new ChangeListener<BinaryPrediction>()
		{

			@Override
			public void changed(ObservableValue<? extends BinaryPrediction> observable, BinaryPrediction oldValue, BinaryPrediction newValue)
			{

				nodeTableViewBinaryPredictionList.getSelectionModel().select(newValue);

			}

		});
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getClassifierName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("extractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, Integer>("frameSize"));
		this.nodeTableColumnErrorClassName.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getErrorClassName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<BinaryPrediction, String>("getID"));
	}
}
