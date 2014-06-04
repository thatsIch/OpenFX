package de.thatsich.openfx.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;
import de.thatsich.openfx.errorgeneration.api.model.IErrorEntries;
import de.thatsich.openfx.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatureExtractors;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import de.thatsich.openfx.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.openfx.featureextraction.intern.control.command.FeatureInitCommander;
import de.thatsich.openfx.featureextraction.intern.control.command.IFeatureCommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVectorSet;
import de.thatsich.openfx.featureextraction.intern.control.handler.ExtractFeatureVectorSetSucceededHandler;
import de.thatsich.openfx.featureextraction.intern.control.handler.RemoveFeatureVectorSetSucceededHandler;
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
	@Inject private IFeatureVectorSets featureVectors;

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

		this.nodeChoiceBoxFeatureExtractor.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> commander.createSetLastFeatureExtractorIndexCommand(newValue.intValue()).start());
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

			featureState.frameSize().set(frameSize);
			commander.createSetLastFrameSizeCommand(frameSize).start();
			log.info("Initiated SetLastFrameSizeCommand with " + frameSize + ".");
		});
		this.log.info("Bound FrameSize to Config.");
	}

	/**
	 * Simple Validator Binding for Buttons
	 */
	private void bindButtons()
	{
		this.nodeButtonExtractFeatureVector.disableProperty().bind(this.errorEntryList.selectedErrorEntry().isNull().or(this.nodeChoiceBoxFeatureExtractor.valueProperty().isNull()));
		this.nodeButtonRemoveFeatureVector.disableProperty().bind(this.featureVectors.selectedSet().isNull());
		this.nodeButtonResetFeatureVectorList.disableProperty().bind(this.featureVectors.list().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================
	@FXML
	private void onExtractAction()
	{
		final ExtractFeatureVectorSetCommand extractCommand = this.commander.createExtractFeatureVectorCommand(this.featureState.path().get(), this.errorEntryList.selectedErrorEntry().get(), this.featureExtractors.selected().get(), this.featureState.frameSize().get(), this.nodeCheckBoxSmooth.isSelected(), this.nodeCheckBoxThreshold.isSelected(), this.nodeCheckBoxDenoising.isSelected());
		extractCommand.setOnSucceededCommandHandler(ExtractFeatureVectorSetSucceededHandler.class);
		extractCommand.start();
		this.log.info("FeatureVector deleted and removed from FeatureVectorList.");
	}

	@FXML
	private void onRemoveAction()
	{
		final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(this.featureVectors.selectedSet().get());
		command.setOnSucceededCommandHandler(RemoveFeatureVectorSetSucceededHandler.class);
		command.start();
		this.log.info("FeatureVectorSet deletion instantiated.");
	}

	@FXML
	private void onResetAction()
	{
		final List<FeatureVectorSet> list = this.featureVectors.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized Executor for resetting all FeatureVectors.");

		for (FeatureVectorSet set : list)
		{
			final DeleteFeatureVectorSetCommand command = this.commander.createRemoveFeatureVectorSetCommand(set);
			command.setOnSucceededCommandHandler(RemoveFeatureVectorSetSucceededHandler.class);
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
