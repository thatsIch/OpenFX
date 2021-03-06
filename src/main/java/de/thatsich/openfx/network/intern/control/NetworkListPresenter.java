package de.thatsich.openfx.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
import de.thatsich.openfx.network.intern.control.command.commands.SetLastNetworkIndexCommand;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private NetworkInitCommander init;
	@Inject private INetworks networks;
	@Inject private INetworkCommandProvider provider;

	// Nodes
	@FXML private TableView<ITrainedNetwork> nodeTableViewNetworkList;
	@FXML private TableColumn<ITrainedNetwork, String> nodeTableColumnDate;
	@FXML private TableColumn<ITrainedNetwork, String> nodeTableColumnID;
	@FXML private TableColumn<ITrainedNetwork, Long> nodeTableColumnTrainTime;

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
		this.nodeTableViewNetworkList.itemsProperty().bind(this.networks.list());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelection()
	{
		this.nodeTableViewNetworkList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldValue, newValue) -> {
			this.networks.selected().set(newValue);
			this.log.info("Set Selected BinaryPrediction in Model.");

			final int index = this.nodeTableViewNetworkList.getSelectionModel().getSelectedIndex();
			final SetLastNetworkIndexCommand command = this.provider.createSetLastNetworkIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.networks.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewNetworkList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnDate.setCellValueFactory(cellData -> cellData.getValue().date());
		this.nodeTableColumnID.setCellValueFactory(cellData -> cellData.getValue().id());
		this.nodeTableColumnTrainTime.setCellValueFactory(cellData -> cellData.getValue().trainTime().asObject());
	}
}
