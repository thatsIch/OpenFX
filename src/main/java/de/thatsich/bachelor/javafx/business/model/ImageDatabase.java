package de.thatsich.bachelor.javafx.business.model;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.Log;

/**
 * Model class for storing the images
 * and a simple interface importing
 * images through Path Objects
 * 
 * @author Minh
 *
 */
public class ImageDatabase {

	// Fields
	final private Path inputFolderPath;
	
	// properties
	final private ObjectProperty<ImageEntry> imageEntry = new SimpleObjectProperty<ImageEntry>();
	final private ObjectProperty<ObservableList<ImageEntry>> imageEntries = new ChoiceBox<ImageEntry>().itemsProperty();
	
	// Injects
	final private Log log;
	
	
	/**
	 * Injected CTOR
	 * 
	 * @param log Logger
	 * @param provider CommandProvider
	 * @throws IOException If the Folder for input and output could not be created
	 */
	@Inject
	private ImageDatabase(Log log) throws IOException {
		this.log = log;
		
		this.inputFolderPath = Paths.get("input");
		if (Files.notExists(this.inputFolderPath) || !Files.isDirectory(this.inputFolderPath)) Files.createDirectory(this.inputFolderPath);
			
		this.initImagePaths();
		this.resetSelection();
	}
	
	/**
	 * Initialize the ImagePath variable 
	 * with all images in the input folder 
	 * supported by OpenCV and JavaFX (jpg, png)
	 */
	private void initImagePaths() {

		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.inputFolderPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				this.imageEntries.get().add(new ImageEntry(child.toAbsolutePath()));
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + this.imageEntries.get().size() + ".");
	}
	
	/**
	 * 
	 */
	public void resetSelection() {
		if (this.imageEntries.get().size() > 0) {
			this.imageEntry.set(this.imageEntries.get().get(0));
			this.log.info("Reset to first ImageEntry.");
		}
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
	public Path getInputPath() { return this.inputFolderPath; }
	public ObservableList<ImageEntry> getImageEntries() { return this.imageEntries.get(); }
	public ImageEntry getSelectedImageEntry() { return this.imageEntry.get(); }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setImageEntry(ImageEntry entry) { this.imageEntry.set(entry); }
	
	// ==================================================
	// Modifier Implementation
	// ==================================================
	public void addImageEntry(ImageEntry entry) { this.imageEntries.get().add(entry); }
	public void removeImageEntry(ImageEntry entry) { this.imageEntries.get().remove(entry); }
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<ImageEntry>> getImageEntriesProperty() { return this.imageEntries; }
	public ObjectProperty<ImageEntry> getImageEntryProperty() { return this.imageEntry; }	
}
