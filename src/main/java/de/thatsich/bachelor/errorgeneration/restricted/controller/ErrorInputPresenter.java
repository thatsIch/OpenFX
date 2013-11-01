package de.thatsich.bachelor.errorgeneration.restricted.controller;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorState;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.restricted.command.ErrorInitCommander;
import de.thatsich.bachelor.errorgeneration.restricted.command.IErrorCommandProvider;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.ApplyErrorCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.DeleteErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.handler.DeleteErrorEntrySucceededHandler;
import de.thatsich.bachelor.errorgeneration.restricted.controller.handler.ErrorApplicationSucceededHandler;
import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.core.javafx.component.IntegerField;

/**
 * Presenter
 * 
 * Represents the input controls for the error column
 * 
 * @author Minh
 *
 */
public class ErrorInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ChoiceBox<IErrorGenerator> nodeChoiceBoxErrorGenerator;
	@FXML private IntegerField nodeIntegerFieldErrorCount;
	
	@FXML private Button nodeButtonGenerateErrors;
	@FXML private Button nodeButtonPermutateErrors;
	@FXML private Button nodeButtonRemoveError;
	@FXML private Button nodeButtonResetErrors;
	
	// Injects
	@Inject private IImageEntries imageEntryList;
	@Inject private IErrorState errorState;
	@Inject private IErrorEntries errorEntryList;
	@Inject private IErrorGenerators errorGeneratorList;
	@Inject private IErrorCommandProvider commander;
	
	@Inject ErrorInitCommander initCommander;
	
	// ================================================== 
	// Initializable Implementation 
	// ==================================================
	@Override
	protected void initComponents() {
		
	}

	@Override
	protected void bindComponents() {
		this.bindChoiceBoxErrorGenerator();
		this.bindIntegerFieldErrorCount();
		this.bindButton();
	}
	
	
	

	
	// ================================================== 
	// Bindings Implementation 
	// ==================================================	
	/**
	 * Bind ChoiceBoxErrorGenerator together with its corresponding Model part.
	 */
	private void bindChoiceBoxErrorGenerator() {
		this.nodeChoiceBoxErrorGenerator.setConverter(new StringConverter<IErrorGenerator>() {
			@Override public String toString(IErrorGenerator errorGenerator) { return errorGenerator.getName(); }
			@Override public IErrorGenerator fromString(String arg0) { return null; }
		});
		this.log.info("Set up StringErrorGeneratorConverter for proper name display.");
		
		this.nodeChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.errorGeneratorList.getErrorGeneratorListProperty());
		this.nodeChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.errorGeneratorList.getSelectedErrorGeneratorProperty());
		this.log.info("Bound ChoiceBoxErrorGenerator to Model.");
		
		this.nodeChoiceBoxErrorGenerator.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				SetLastErrorGeneratorIndexCommand command = commander.createSetLastErrorGeneratorIndexCommand(newValue.intValue());
				command.start();
			}
		});
		this.log.info("Bound ChoiceBoxErrorGenerator to Config.");
	}
	
	/**
	 * Bind TextFieldErrorCount to the Model and tries to validate the input to only non-negative inputs
	 * and initialize the value for it
	 */
	private void bindIntegerFieldErrorCount() {
		this.nodeIntegerFieldErrorCount.valueProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				final int count = newValue.intValue();
				errorState.getErrorLoopCountProperty().set(count);
				final SetLastErrorCountCommand command = commander.createSetLastErrorCountCommand(count);
				command.start();
				log.info("Initiated SetLastErrorCountCommand with " + count + ".");
			}
		});
	}
	
	private void bindButton() {
		this.nodeButtonGenerateErrors.disableProperty().bind(this.imageEntryList.selectedImageEntryProperty().isNull().or(this.nodeChoiceBoxErrorGenerator.valueProperty().isNull()).or(this.nodeIntegerFieldErrorCount.valueProperty().isEqualTo(0)));
		this.nodeButtonPermutateErrors.disableProperty().bind(this.imageEntryList.imageEntriesmageEntryListProperty().emptyProperty().or(this.nodeChoiceBoxErrorGenerator.valueProperty().isNull()).or(this.nodeIntegerFieldErrorCount.valueProperty().isEqualTo(0)));
		this.nodeButtonRemoveError.disableProperty().bind(this.errorEntryList.getSelectedErrorEntryProperty().isNull());
		this.nodeButtonResetErrors.disableProperty().bind(this.errorEntryList.getErrorEntryListProperty().emptyProperty());
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
	/**
	 * Generates a single error output depending on selected image, generator and amount
	 */
	@FXML private void onGenerateErrorsAction() {
		final ImageEntry imageEntry = this.imageEntryList.getSelectedImageEntry();
		final Mat image = imageEntry.getImageMat().clone();
		final IErrorGenerator generator = this.errorGeneratorList.getSelectedErrorGenerator();
		final int loops = this.errorState.getErrorLoopCount();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(loops);
		this.log.info("Initialized Executor for generating all Errors.");
		
		for (int step = 0; step < loops; step++) {
			final String dateTime = generator.getName() + "_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + "_" + UUID.randomUUID().toString();
			final Path imagePath = this.errorState.getErrorEntryFolderPath().resolve(dateTime + ".png");
			this.log.info("Path: " + imagePath);
			
			final ApplyErrorCommand command = this.commander.createApplyErrorCommand(image, imagePath, generator);
			command.setOnSucceededCommandHandler(ErrorApplicationSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("File created and added to ErrorList.");
		
		executor.execute(new Runnable() {
			@Override public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	/**
	 * Delete and remove the selected ErrorEntry
	 */
	@FXML private void onRemoveErrorAction() {
		final ErrorEntry entry = this.errorEntryList.getSelectedErrorEntry();
		this.log.info("Fetched selected ErrorEntry.");
		
		final DeleteErrorEntryCommand command = this.commander.createDeleteErrorEntryCommand(entry);
		command.setOnSucceededCommandHandler(DeleteErrorEntrySucceededHandler.class);
		command.start();
		this.log.info("File deleted and removed from ErrorList.");
	}
	
	/**
	 * Cross-Set of all input images to selected error generator
	 */
	@FXML private void onPermutateErrorsAction() {
		final ObservableList<ImageEntry> imageEntries = this.imageEntryList.imageEntriesmageEntryListProperty();
		final IErrorGenerator generator = this.errorGeneratorList.getSelectedErrorGenerator();
		final int loops = this.errorState.getErrorLoopCount();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(imageEntries.size() * loops);
		final Path errorEntryFolderPath = this.errorState.getErrorEntryFolderPath();
		this.log.info("Initialized Executor for permutating all Errors.");
		
		for (ImageEntry entry : imageEntries) {
			final Mat image = entry.getImageMat().clone();
			for (int step = 0; step < loops; step++) {
				final String dateTime = generator.getName() + "_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + "_" + UUID.randomUUID().toString();
				Path imagePath = errorEntryFolderPath.resolve(dateTime + ".png");
				this.log.info("Path: " + imagePath);
				
				final ApplyErrorCommand command = this.commander.createApplyErrorCommand(image, imagePath, generator);
				command.setOnSucceededCommandHandler(ErrorApplicationSucceededHandler.class);
				command.setExecutor(executor);
				command.start();
			}
		}
		this.log.info("Files created and added to ErrorList.");
		
		executor.execute(new Runnable() {
			@Override public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	/**
	 * Deletes each file and remove each entry
	 */
	@FXML private void onResetErrorsAction() {
		final List<ErrorEntry> errorEntryList = this.errorEntryList.getErrorEntryListProperty();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(errorEntryList.size());
		this.log.info("Initialized Executor for resetting all Errors.");
		
		for (ErrorEntry entry : errorEntryList) {
			final DeleteErrorEntryCommand command = this.commander.createDeleteErrorEntryCommand(entry);
			command.setOnSucceededCommandHandler(DeleteErrorEntrySucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("File Deletion executed.");
		}
		
		executor.execute(new Runnable() {
			@Override public void run() { System.gc(); }
		});
		this.log.info("Running Garbage Collector.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
