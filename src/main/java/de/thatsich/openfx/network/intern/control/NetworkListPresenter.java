package de.thatsich.openfx.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.network.api.control.entity.INetwork;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
import de.thatsich.openfx.network.intern.control.command.commands.SetLastNetworkIndexCommand;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
	@FXML private TableView<INetwork> nodeTableViewNetworkList;
	@FXML private TableColumn<INetwork, String> nodeTableColumnClassifierName;
	@FXML private TableColumn<INetwork, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<INetwork, Integer> nodeTableColumnFrameSize;
	@FXML private TableColumn<INetwork, String> nodeTableColumnErrorClassName;
	@FXML private TableColumn<INetwork, String> nodeTableColumnID;

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
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<>("getClassifierName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<>("extractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<>("tileSize"));
		this.nodeTableColumnErrorClassName.setCellValueFactory(new PropertyValueFactory<>("getErrorClassName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<>("getID"));
	}
}
