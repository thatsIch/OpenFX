package de.thatsich.openfx.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.api.model.INetworkState;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
import de.thatsich.openfx.network.intern.control.command.commands.CreateTrainedNetworkCommand;
import de.thatsich.openfx.network.intern.control.command.commands.DeleteNetworkCommand;
import de.thatsich.openfx.network.intern.control.handler.DeleteNetworkSucceededHandler;
import de.thatsich.openfx.network.intern.control.handler.TrainNetworkSucceededHandler;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessors;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
	@Inject private IImages images;
	@Inject private IErrorGenerators errorGenerators;
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private IPreProcessors preProcessors;
	@Inject private IBinaryClassifiers binaryClassifiers;

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
		this.nodeButtonTrainNetwork.disableProperty().bind(this.images.list().emptyProperty());
		this.nodeButtonDeleteNetwork.disableProperty().bind(this.networks.selected().isNull());
		this.nodeButtonResetNetwork.disableProperty().bind(this.networks.list().emptyProperty());
	}

	@Override
	protected void initComponents()
	{

	}

	@FXML
	private void onTrainNetworkAction()
	{
		final List<IImage> images = this.images.list();
		final List<IErrorGenerator> errorGenerators = this.errorGenerators.list();
		final List<IFeatureExtractor> featureExtractors = this.featureExtractors.list();
		final List<IPreProcessor> preProcessors = this.preProcessors.list();
		final List<IBinaryClassifier> binaryClassifiers = this.binaryClassifiers.list();

		final CreateTrainedNetworkCommand command = this.provider.createTrainedNetworkCommand(images, errorGenerators, featureExtractors, preProcessors, binaryClassifiers);
		command.setOnSucceededCommandHandler(TrainNetworkSucceededHandler.class);
		command.start();
		this.log.info("Initiated Creation of Network.");
	}

	@FXML
	private void onDeleteNetworkAction()
	{
		final ITrainedNetwork selected = this.networks.selected().get();
		final DeleteNetworkCommand command = this.provider.createDeleteNetworkCommand(selected);
		command.setOnSucceededCommandHandler(DeleteNetworkSucceededHandler.class);
		command.start();
		this.log.info("Initiated Delete of Network.");
	}

	@FXML
	private void onResetNetworkAction()
	{
		final List<ITrainedNetwork> networkList = this.networks.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(networkList.size());

		for (final ITrainedNetwork network : networkList)
		{
			final DeleteNetworkCommand command = this.provider.createDeleteNetworkCommand(network);
			command.setOnSucceededCommandHandler(DeleteNetworkSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("Initiated Reset of all Networks.");

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
