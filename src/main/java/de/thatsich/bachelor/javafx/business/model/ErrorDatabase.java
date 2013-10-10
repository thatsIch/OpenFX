package de.thatsich.bachelor.javafx.business.model;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.opencv.error.LineError;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.IErrorGenerator;

public class ErrorDatabase {
	
	// fields
	final private Path errorFolderPath;
	
	// Injects
	final private Log log;
	
	// Properties
	final private ObjectProperty<ObservableList<IErrorGenerator>> errorGenerators = new ChoiceBox<IErrorGenerator>().itemsProperty();
	final private ObjectProperty<IErrorGenerator> errorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	
	final private ObjectProperty<ObservableList<ErrorEntry>> errorEntries = new ChoiceBox<ErrorEntry>().itemsProperty();
	final private ObjectProperty<ErrorEntry> errorEntry = new SimpleObjectProperty<ErrorEntry>();
	
	final private IntegerProperty errorLoopCount = new SimpleIntegerProperty();
	
	/**
	 * Injected CTOR
	 * Creates an Error-Folder where all the errors will be stored.
	 * Initialize the ErrorGenerators
	 * and load all already created errors from HDD
	 * 
	 * @param log Logger
	 * @throws IOException If Error Folder cannot be created.
	 */
	@Inject
	private ErrorDatabase(Log log) throws IOException {
		this.log = log;
		
		this.errorFolderPath = Paths.get("error");
		if (Files.notExists(this.errorFolderPath) || !Files.isDirectory(this.errorFolderPath)) Files.createDirectory(this.errorFolderPath);
		
		this.initErrorGenerators();
		this.initErrorPaths();
		this.resetSelection();
	}
	
	/**
	 * Initialize all wanted Error Generators implementing Interface IErrorGenerator
	 */
	private void initErrorGenerators() {
		this.errorGenerators.get().addAll(
			new LineError()
		);
		this.log.info("Initialized Error Generators.");
		
		if (this.errorGenerators.get().size() > 0) {
			this.errorGenerator.set(this.errorGenerators.get().get(0));
			this.log.info("Initialized with first Error Generator.");
		}
	}
	
	/**
	 * Fetches all files compatible with OpenCV and JavaFX
	 */
	private void initErrorPaths() {
		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.errorFolderPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				this.errorEntries.get().add(new ErrorEntry(child));
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + this.errorEntries.get().size() + ".");
	}
	
	/**
	 * Resets the selection in the Model.
	 */
	public void resetSelection() {
		if (this.errorEntries.get().size() > 0) {
			this.errorEntry.set(this.errorEntries.get().get(0));
			this.log.info("Initialized with first Image.");
		}
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
	public int getErrorLoopCount() { return this.errorLoopCount.get(); }
	public ErrorEntry getSelectedErrorEntry() { return this.errorEntry.get(); }
	public ObservableList<ErrorEntry> getErrorEntries() { return this.errorEntries.get(); }
	public IErrorGenerator getErrorGenerator() { return this.errorGenerator.get(); }
	public Path getErrorFolderPath() { return this.errorFolderPath; }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setErrorEntry(ErrorEntry entry) { this.errorEntry.set(entry); }
	public void setErrorLoopCount(int count) { this.errorLoopCount.set(count); }
	
	// ==================================================
	// Modifier Implementation
	// ==================================================
	public void removeErrorEntry(ErrorEntry entry) { this.errorEntries.get().remove(entry); }
	public void addErrorEntry(ErrorEntry entry) { this.errorEntries.get().add(entry); }
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<IErrorGenerator>> getErrorGeneratorsProperty() { return this.errorGenerators; }
	public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty() { return this.errorGenerator; }
	public ObjectProperty<ObservableList<ErrorEntry>> getErrorEntriesProperty() { return this.errorEntries; }
	public ObjectProperty<ErrorEntry> getErrorEntryProperty() { return this.errorEntry; }
	public IntegerProperty getErrorLoopCountProperty() { return this.errorLoopCount; }
}
