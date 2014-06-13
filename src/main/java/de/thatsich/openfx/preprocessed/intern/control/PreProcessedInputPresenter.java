package de.thatsich.openfx.preprocessed.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.preprocessed.api.model.IPreProcessedState;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;
import de.thatsich.openfx.preprocessed.intern.control.command.PreProcessedInitCommander;
import de.thatsich.openfx.preprocessed.intern.control.command.commands.CreatePreProcessedFeatureCommand;
import de.thatsich.openfx.preprocessed.intern.control.command.commands.RemovePreProcessedFeatureCommand;
import de.thatsich.openfx.preprocessed.intern.control.command.provider.IPreProcessedCommandProvider;
import de.thatsich.openfx.preprocessed.intern.control.handler.CreatePreProcessedSucceededHandler;
import de.thatsich.openfx.preprocessed.intern.control.handler.DeletePreProcessedSucceededHandler;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.api.model.ITrainedPreProcessors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class PreProcessedInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PreProcessedInitCommander init;
	@Inject private IPreProcessedCommandProvider provider;
	@Inject private IPreProcessedState state;
	@Inject private IPreProcesseds preProcesseds;
	@Inject private ITrainedPreProcessors trainedPreProcessors;
	@Inject private IFeatures features;

	// Nodes
	@FXML private Button nodeButtonPreProcessFeature;
	@FXML private Button nodeButtonRemovePreProcessedFeature;
	@FXML private Button nodeButtonResetPreProcessedFeatures;

	@Override
	protected void bindComponents()
	{
		this.bindButtons();
	}

	@Override
	protected void initComponents()
	{
	}

	// ==================================================
	// Bindings Implementation
	// ==================================================

	/**
	 * Simple Validator Binding for Buttons
	 */
	private void bindButtons()
	{
		this.nodeButtonPreProcessFeature.disableProperty().bind(this.features.selected().isNull().or(this.trainedPreProcessors.selected().isNull()));
		this.nodeButtonRemovePreProcessedFeature.disableProperty().bind(this.preProcesseds.selected().isNull());
		this.nodeButtonResetPreProcessedFeatures.disableProperty().bind(this.preProcesseds.list().emptyProperty());
	}

	// ==================================================
	// GUI Implementation
	// ==================================================
	@FXML
	private void onPreProcessAction()
	{
		final ITrainedPreProcessor preProcessor = this.trainedPreProcessors.selected().get();
		final IFeature feature = this.features.selected().get();

		final CreatePreProcessedFeatureCommand command = this.provider.createCreatePreProcessedFeatureCommand(preProcessor, feature);
		command.setOnSucceededCommandHandler(CreatePreProcessedSucceededHandler.class);
		command.start();
		this.log.info("PreProcessed feature.");
	}

	@FXML
	private void onRemoveAction()
	{
		final IFeature feature = this.features.selected().get();

		final RemovePreProcessedFeatureCommand command = this.provider.createRemovePreProcessedFeatureCommand(feature);
		command.setOnSucceededCommandHandler(DeletePreProcessedSucceededHandler.class);
		command.start();
		this.log.info("PreProcessed Deletion instantiated.");
	}

	@FXML
	private void onResetAction()
	{
		final List<IFeature> list = this.features.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");

		for (IFeature feature : list)
		{
			final RemovePreProcessedFeatureCommand command = this.provider.createRemovePreProcessedFeatureCommand(feature);
			command.setOnSucceededCommandHandler(DeletePreProcessedSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("PreProcessed Deletion executed.");
		}

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
