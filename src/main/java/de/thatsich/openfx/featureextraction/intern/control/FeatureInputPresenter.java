package de.thatsich.openfx.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;
import de.thatsich.openfx.errorgeneration.api.model.IErrorEntries;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.intern.control.command.FeatureInitCommander;
import de.thatsich.openfx.featureextraction.intern.control.command.IFeatureCommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.DeleteFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.ExtractFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.handler.ExtractFeatureSucceededHandler;
import de.thatsich.openfx.featureextraction.intern.control.handler.RemoveFeatureSucceededHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class FeatureInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private FeatureInitCommander initCommander;
	@Inject private IFeatureCommandProvider commander;
	@Inject private IErrorEntries errorEntryList;
	@Inject private IFeatureExtractors featureExtractors;
	@Inject private IFeatureState featureState;
	@Inject private IFeatures features;

	// Nodes
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	@FXML private IntegerField nodeIntegerFieldFrameSize;
	@FXML private Button nodeButtonExtractFeatureVector;
	@FXML private Button nodeButtonRemoveFeatureVector;
	@FXML private Button nodeButtonResetFeatureVectorList;
	@FXML private CheckBox nodeCheckBoxSmooth;
	@FXML private CheckBox nodeCheckBoxThreshold;
	@FXML private CheckBox nodeCheckBoxDenoising;

	@Override
	protected void bindComponents()
	{
		this.bindChoiceBoxFeatureExtractor();
		this.bindIntegerFieldFrameSize();
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
	 * Bind ChoiceBoxFeatureExtractor to the Model.
	 */
	private void bindChoiceBoxFeatureExtractor()
	{
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringConverter<IFeatureExtractor>()
		{
			@Override
			public String toString(IFeatureExtractor featureGenerator) { return featureGenerator.getName(); }

			@Override
			public IFeatureExtractor fromString(String arg0) { return null; }
		});
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");

		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.featureExtractors.list());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.featureExtractors.selected());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");

		this.nodeChoiceBoxFeatureExtractor.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> this.commander.createSetLastFeatureExtractorIndexCommand(newValue.intValue()).start());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Config.");
	}

	/**
	 *
	 */
	private void bindIntegerFieldFrameSize()
	{
		this.nodeIntegerFieldFrameSize.value.bindBidirectional(this.featureState.frameSize());
		this.log.info("Bound FrameSize to DataBase");

		this.nodeIntegerFieldFrameSize.value.addListener((paramObservableValue, oldValue, newValue) -> {
			final int frameSize = newValue.intValue();

			this.featureState.frameSize().set(frameSize);
			this.commander.createSetLastFrameSizeCommand(frameSize).start();
			this.log.info("Initiated SetLastFrameSizeCommand with " + frameSize + ".");
		});
		this.log.info("Bound FrameSize to Config.");
	}

	/**
	 * Simple Validator Binding for Buttons
	 */
	private void bindButtons()
	{
		this.nodeButtonExtractFeatureVector.disableProperty().bind(this.errorEntryList.selectedErrorEntry().isNull().or(this.nodeChoiceBoxFeatureExtractor.valueProperty().isNull()));
		this.nodeButtonRemoveFeatureVector.disableProperty().bind(this.features.selectedFeature().isNull());
		this.nodeButtonResetFeatureVectorList.disableProperty().bind(this.features.list().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================
	@FXML
	private void onExtractAction()
	{
		final ExtractFeatureCommand extractCommand = this.commander.createExtractFeatureVectorCommand(this.featureState.path().get(), this.errorEntryList.selectedErrorEntry().get(), this.featureExtractors.selected().get(), this.featureState.frameSize().get(), this.nodeCheckBoxSmooth.isSelected(), this.nodeCheckBoxThreshold.isSelected(), this.nodeCheckBoxDenoising.isSelected());
		extractCommand.setOnSucceededCommandHandler(ExtractFeatureSucceededHandler.class);
		extractCommand.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}

	@FXML
	private void onRemoveAction()
	{
		final DeleteFeatureCommand command = this.commander.createRemoveFeatureVectorSetCommand(this.features.selectedFeature().get());
		command.setOnSucceededCommandHandler(RemoveFeatureSucceededHandler.class);
		command.start();
		this.log.info("FeatureVectorSet deletion instantiated.");
	}

	@FXML
	private void onResetAction()
	{
		final List<IFeature> list = this.features.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");

		for (IFeature feature : list)
		{
			final DeleteFeatureCommand command = this.commander.createRemoveFeatureVectorSetCommand(feature);
			command.setOnSucceededCommandHandler(RemoveFeatureSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("FeatureVector Deletion executed.");
		}

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}