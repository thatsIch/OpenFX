package de.thatsich.openfx.errorgeneration.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import de.thatsich.openfx.errorgeneration.intern.control.command.ErrorInitCommander;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.DeleteErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.handler.CreateErrorSucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.handler.DeleteErrorEntrySucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import org.opencv.core.Mat;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Presenter
 *
 * Represents the input controls for the error column
 *
 * @author Minh
 */
public class ErrorInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ErrorInitCommander init;
	@Inject private IImages images;
	@Inject private IErrorState state;
	@Inject private IErrors errors;
	@Inject private IErrorGenerators generators;
	@Inject private IErrorCommandProvider provider;

	// Nodes
	@FXML private ChoiceBox<IErrorGenerator> nodeChoiceBoxErrorGenerator;
	@FXML private IntegerField nodeIntegerFieldErrorCount;
	@FXML private Button nodeButtonGenerateErrors;
	@FXML private Button nodeButtonPermutateErrors;
	@FXML private Button nodeButtonRemoveError;
	@FXML private Button nodeButtonResetErrors;

	@Override
	protected void bindComponents()
	{
		this.bindChoiceBoxErrorGenerator();
		this.bindIntegerFieldErrorCount();
		this.bindButton();
	}

	// ==================================================
	// Initializable Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{

	}


	// ================================================== 
	// Bindings Implementation 
	// ==================================================	

	/**
	 * Bind ChoiceBoxErrorGenerator together with its corresponding Model part.
	 */
	private void bindChoiceBoxErrorGenerator()
	{
		this.nodeChoiceBoxErrorGenerator.setConverter(new StringConverter<IErrorGenerator>()
		{
			@Override
			public String toString(IErrorGenerator errorGenerator) { return errorGenerator.getName(); }

			@Override
			public IErrorGenerator fromString(String arg0) { return null; }
		});
		this.log.info("Set up StringErrorGeneratorConverter for proper name display.");

		this.nodeChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.generators.list());
		this.nodeChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.generators.selected());
		this.log.info("Bound ChoiceBoxErrorGenerator to Model.");

		this.nodeChoiceBoxErrorGenerator.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			SetLastErrorGeneratorIndexCommand command = this.provider.createSetLastErrorGeneratorIndexCommand(newValue.intValue());
			command.start();
		});
		this.log.info("Bound ChoiceBoxErrorGenerator to Config.");
	}

	/**
	 * Bind TextFieldErrorCount to the Model and tries to validate the input to only non-negative inputs
	 * and initialize the value for it
	 */
	private void bindIntegerFieldErrorCount()
	{
		this.nodeIntegerFieldErrorCount.value.addListener((observable, oldValue, newValue) -> {
			final int count = newValue.intValue();
			this.state.loopCount().set(count);
			final SetLastErrorCountCommand command = this.provider.createSetLastErrorCountCommand(count);
			command.start();
			this.log.info("Initiated SetLastErrorCountCommand with " + count + ".");
		});
	}

	private void bindButton()
	{
		this.nodeButtonGenerateErrors.disableProperty().bind(this.images.selected().isNull().or(this.nodeChoiceBoxErrorGenerator.valueProperty().isNull()).or(this.nodeIntegerFieldErrorCount.value.isEqualTo(0)));
		this.nodeButtonPermutateErrors.disableProperty().bind(this.images.list().emptyProperty().or(this.nodeChoiceBoxErrorGenerator.valueProperty().isNull()).or(this.nodeIntegerFieldErrorCount.value.isEqualTo(0)));
		this.nodeButtonRemoveError.disableProperty().bind(this.errors.selected().isNull());
		this.nodeButtonResetErrors.disableProperty().bind(this.errors.list().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================

	/**
	 * Generates a single error output depending on selected image, generator and amount
	 */
	@FXML
	private void onGenerateErrorsAction()
	{
		final IImage imageEntry = this.images.selected().get();
		final Mat image = imageEntry.getImageMat().clone();
		final IErrorGenerator generator = this.generators.selected().get();
		final int loops = this.state.loopCount().get();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(loops);
		this.log.info("Initialized Executor for generating all Errors.");

		final String errorClass = generator.getName();

		for (int step = 0; step < loops; step++)
		{
			final CreateErrorCommand command = this.provider.createApplyErrorCommand(errorClass, image, generator);
			command.setOnSucceededCommandHandler(CreateErrorSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("File created and added to ErrorList.");

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Delete and remove the selected ErrorEntry
	 */
	@FXML
	private void onRemoveErrorAction()
	{
		final IError entry = this.errors.selected().get();
		this.log.info("Fetched selected ErrorEntry.");

		final DeleteErrorCommand command = this.provider.createDeleteErrorEntryCommand(entry);
		command.setOnSucceededCommandHandler(DeleteErrorEntrySucceededHandler.class);
		command.start();
		this.log.info("File deleted and removed from ErrorList.");
	}

	/**
	 * Cross-Set of all input images to selected error generator
	 */
	@FXML
	private void onPermutateErrorsAction()
	{
		final ObservableList<IImage> imageEntries = this.images.list();
		final IErrorGenerator generator = this.generators.selected().get();
		final int loops = this.state.loopCount().get();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(imageEntries.size() * loops);
		final String errorClass = generator.getName();
		this.log.info("Initialized Executor for permutating all Errors.");

		for (IImage entry : imageEntries)
		{
			final Mat image = entry.getImageMat().clone();
			for (int step = 0; step < loops; step++)
			{
				final CreateErrorCommand command = this.provider.createApplyErrorCommand(errorClass, image, generator);
				command.setOnSucceededCommandHandler(CreateErrorSucceededHandler.class);
				command.setExecutor(executor);
				command.start();
			}
		}
		this.log.info("Files created and added to ErrorList.");

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Deletes each file and remove each entry
	 */
	@FXML
	private void onResetErrorsAction()
	{
		final List<IError> errorList = this.errors.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(errorList.size());
		this.log.info("Initialized Executor for resetting all Errors.");

		for (IError entry : errorList)
		{
			final DeleteErrorCommand command = this.provider.createDeleteErrorEntryCommand(entry);
			command.setOnSucceededCommandHandler(DeleteErrorEntrySucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("File Deletion executed.");
		}

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
