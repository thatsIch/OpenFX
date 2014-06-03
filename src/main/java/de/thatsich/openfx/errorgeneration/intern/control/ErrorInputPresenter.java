package de.thatsich.openfx.errorgeneration.intern.control;

import com.google.inject.Inject;
import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorEntries;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.intern.control.command.ErrorInitCommander;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorEntryCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.DeleteErrorEntryCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.openfx.errorgeneration.intern.control.handler.CreateErrorEntrySucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.handler.DeleteErrorEntrySucceededHandler;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.ImageEntry;
import de.thatsich.openfx.imageprocessing.api.model.IImageEntries;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import org.opencv.core.Mat;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
	@Inject private ErrorInitCommander initCommander;
	@Inject private IImageEntries imageEntryList;
	@Inject private IErrorState state;
	@Inject private IErrorEntries errorEntryList;
	@Inject private IErrorGenerators errorGeneratorList;
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

		this.nodeChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.errorGeneratorList.errorGenerators());
		this.nodeChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.errorGeneratorList.selectedErrorGenerator());
		this.log.info("Bound ChoiceBoxErrorGenerator to Model.");

		this.nodeChoiceBoxErrorGenerator.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			SetLastErrorGeneratorIndexCommand command = provider.createSetLastErrorGeneratorIndexCommand(newValue.intValue());
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
			state.loopCount().set(count);
			final SetLastErrorCountCommand command = provider.createSetLastErrorCountCommand(count);
			command.start();
			log.info("Initiated SetLastErrorCountCommand with " + count + ".");
		});
	}

	private void bindButton()
	{
		this.nodeButtonGenerateErrors.disableProperty().bind(this.imageEntryList.selectedImageEntryProperty().isNull().or(this.nodeChoiceBoxErrorGenerator.valueProperty().isNull()).or(this.nodeIntegerFieldErrorCount.value.isEqualTo(0)));
		this.nodeButtonPermutateErrors.disableProperty().bind(this.imageEntryList.imageEntryListProperty().emptyProperty().or(this.nodeChoiceBoxErrorGenerator.valueProperty().isNull()).or(this.nodeIntegerFieldErrorCount.value.isEqualTo(0)));
		this.nodeButtonRemoveError.disableProperty().bind(this.errorEntryList.selectedErrorEntry().isNull());
		this.nodeButtonResetErrors.disableProperty().bind(this.errorEntryList.errorEntries().emptyProperty());
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
		final ImageEntry imageEntry = this.imageEntryList.getSelectedImageEntry();
		final Mat image = imageEntry.getImageMat().clone();
		final IErrorGenerator generator = this.errorGeneratorList.selectedErrorGenerator().get();
		final int loops = this.state.loopCount().get();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(loops);
		this.log.info("Initialized Executor for generating all Errors.");

		for (int step = 0; step < loops; step++)
		{
			final String dateTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + "_" + generator.getName() + "_" + UUID.randomUUID().toString();
			final Path imagePath = this.state.path().get().resolve(dateTime);
			this.log.info("Path: " + imagePath);

			final CreateErrorEntryCommand command = this.provider.createApplyErrorCommand(image, imagePath, generator);
			command.setOnSucceededCommandHandler(CreateErrorEntrySucceededHandler.class);
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
		final ErrorEntry entry = this.errorEntryList.selectedErrorEntry().get();
		this.log.info("Fetched selected ErrorEntry.");

		final DeleteErrorEntryCommand command = this.provider.createDeleteErrorEntryCommand(entry);
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
		final ObservableList<ImageEntry> imageEntries = this.imageEntryList.imageEntryListProperty();
		final IErrorGenerator generator = this.errorGeneratorList.selectedErrorGenerator().get();
		final int loops = this.state.loopCount().get();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(imageEntries.size() * loops);
		final Path errorEntryFolderPath = this.state.path().get();
		this.log.info("Initialized Executor for permutating all Errors.");

		for (ImageEntry entry : imageEntries)
		{
			final Mat image = entry.getImageMat().clone();
			for (int step = 0; step < loops; step++)
			{
				final String dateTime = generator.getName() + "_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + "_" + UUID.randomUUID().toString();
				Path imagePath = errorEntryFolderPath.resolve(dateTime + ".png");
				this.log.info("Path: " + imagePath);

				final CreateErrorEntryCommand command = this.provider.createApplyErrorCommand(image, imagePath, generator);
				command.setOnSucceededCommandHandler(CreateErrorEntrySucceededHandler.class);
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
		final List<ErrorEntry> errorEntryList = this.errorEntryList.errorEntries();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(errorEntryList.size());
		this.log.info("Initialized Executor for resetting all Errors.");

		for (ErrorEntry entry : errorEntryList)
		{
			final DeleteErrorEntryCommand command = this.provider.createDeleteErrorEntryCommand(entry);
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
