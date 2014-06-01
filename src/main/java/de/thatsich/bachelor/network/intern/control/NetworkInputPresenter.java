package de.thatsich.bachelor.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.network.api.core.INetworkState;
import de.thatsich.bachelor.network.api.core.INetworks;
import de.thatsich.bachelor.network.api.entities.Network;
import de.thatsich.bachelor.network.intern.control.command.NetworkInitCommander;
import de.thatsich.bachelor.network.intern.control.command.commands.DeleteNetworkCommand;
import de.thatsich.bachelor.network.intern.control.handler.DeleteNetworkSucceededHandler;
import de.thatsich.bachelor.network.intern.control.provider.INetworkCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkInputPresenter extends AFXMLPresenter
{
	@Inject private NetworkInitCommander commander;

	// Models
	@Inject private INetworkState state;
	@Inject private INetworks networks;

	// Control
	@Inject private INetworkCommandProvider provider;

	// Nodes
	@FXML private Button nodeButtonTrainNetwork;
	@FXML private Button nodeButtonDeleteNetwork;
	@FXML private Button nodeButtonResetNetwork;

	@Override
	protected void bindComponents()
	{
		// Buttons
//		this.nodeButtonTrainNetwork.disabledProperty().bind(this) //TODO bind depending on which model I want to get from
		this.nodeButtonDeleteNetwork.disableProperty().bind(this.networks.getSelectedNetworkProperty().isNull());
		this.nodeButtonResetNetwork.disableProperty().bind(this.networks.getNetworkListProperty().emptyProperty());
	}

	@Override
	protected void initComponents()
	{

	}

	@FXML private void onTrainNetworkAction() {
		final Path path = this.state.getNetworkPath();
//		final N //TODO depending on what I want to depend on

	}

	@FXML private void onDeleteNetworkAction() {
		final Network selected = this.networks.getSelectedNetwork();
		final DeleteNetworkCommand command = this.provider.createDeleteNetworkCommand(selected);
		command.setOnSucceededCommandHandler(DeleteNetworkSucceededHandler.class);
		command.start();
		this.log.info("Initiated Delete of Network.");
	}

	@FXML private void onResetNetworkAction() {
		final List<Network> networkList = this.networks.getNetworkListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(networkList.size());

		for (final Network network : networkList)
		{
			final DeleteNetworkCommand command = this.provider.createDeleteNetworkCommand(network);
			command.setOnSucceededCommandHandler(DeleteNetworkSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("Initiated Reset of all Networks.");

		executor.execute(new Runnable()
		{
			@Override
			public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
