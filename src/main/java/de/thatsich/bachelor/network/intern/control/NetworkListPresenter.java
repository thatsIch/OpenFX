package de.thatsich.bachelor.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.network.api.core.INetworks;
import de.thatsich.bachelor.network.api.entities.Network;
import de.thatsich.bachelor.network.intern.control.command.NetworkInitCommander;
import de.thatsich.bachelor.network.intern.control.command.commands.SetLastNetworkIndexCommand;
import de.thatsich.bachelor.network.intern.control.provider.INetworkCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	@FXML private TableView<Network> nodeTableViewNetworkList;
	@FXML private TableColumn<Network, String> nodeTableColumnClassifierName;
	@FXML private TableColumn<Network, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<Network, Integer> nodeTableColumnFrameSize;
	@FXML private TableColumn<Network, String> nodeTableColumnErrorClassName;
	@FXML private TableColumn<Network, String> nodeTableColumnID;

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
		this.nodeTableViewNetworkList.itemsProperty().bind(this.networks.getNetworkListProperty());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelection()
	{
		this.nodeTableViewNetworkList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Network>()
		{
			@Override
			public void changed(ObservableValue<? extends Network> paramObservableValue, Network oldValue, Network newValue)
			{
				networks.setSelectedNetwork(newValue);
				log.info("Set Selected BinaryPrediction in Model.");

				final int index = nodeTableViewNetworkList.getSelectionModel().getSelectedIndex();
				final SetLastNetworkIndexCommand command = provider.createSetLastNetworkIndexCommand(index);
				command.start();
			}
		});
		this.log.info("Bound Selection to Model.");

		this.networks.getSelectedNetworkProperty().addListener(new ChangeListener<Network>()
		{
			@Override
			public void changed(ObservableValue<? extends Network> observable, Network oldValue, Network newValue)
			{
				nodeTableViewNetworkList.getSelectionModel().select(newValue);
			}
		});
		this.log.info("Bound Model to Selection.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<Network, String>("getClassifierName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<Network, String>("getExtractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<Network, Integer>("getFrameSize"));
		this.nodeTableColumnErrorClassName.setCellValueFactory(new PropertyValueFactory<Network, String>("getErrorClassName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<Network, String>("getID"));
	}
}
