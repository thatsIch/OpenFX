package de.thatsich.bachelor.javafx.model;

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
import javafx.scene.image.Image;
import javafx.util.StringConverter;

import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.core.Log;
import de.thatsich.core.opencv.Images;

public class ImageDatabase {

	// Fields
	final private Path inputFolderPath;
	final private Path outputFolderPath;
	
	// properties
	final private ObjectProperty<ImageEntry> imageEntry = new SimpleObjectProperty<ImageEntry>();
	final private ObjectProperty<ObservableList<ImageEntry>> imageEntries = new ChoiceBox<ImageEntry>().itemsProperty();
	
	// Injects
	final private Log log;
	
	@Inject
	private ImageDatabase(Log log) throws IOException {
		this.log = log;
		
		this.inputFolderPath = Paths.get("input");
		this.outputFolderPath = Paths.get("output");
		
		if (Files.notExists(this.inputFolderPath) || !Files.isDirectory(this.inputFolderPath)) Files.createDirectory(this.inputFolderPath);
		if (Files.notExists(this.outputFolderPath) || !Files.isDirectory(this.outputFolderPath)) Files.createDirectory(this.outputFolderPath); 
			
		this.initImagePaths();
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
		
		if (this.imageEntries.get().size() > 0) {
			this.imageEntry.set(this.imageEntries.get().get(0));
			this.log.info("Initialized with first Image.");
		}
	}
	
	// ==================================================
		// Image Database Implementation
		// ==================================================
		
		/**
		 * Checks for duplicate (already copied images/names)
		 * if no duplicate is found it will copy it into the input folder
		 * and add it to the model
		 * 
		 * @throws IOException
		 */
		public void addImage(Path imagePath) throws IOException {
			
			Path newPath = this.inputFolderPath.resolve(imagePath.getFileName());
			this.log.info("Created new Path: " + newPath);
			
			if (Files.exists(newPath)) {
				this.log.info("Duplicate found: File already exists.");
				return;
			}
					
			Path copiedPath = Files.copy(imagePath, newPath);
			this.log.info("Copied selection ("+ imagePath +") into InputFolder ("+this.inputFolderPath+"): " + copiedPath);
			
			ImageEntry copy = new ImageEntry(copiedPath);
			this.imageEntries.get().add(copy);
			this.log.info("Added copy to ChoiceBoxDisplayImage: " + copiedPath.toString());
			
			this.imageEntry.set(copy);
			this.log.info("Set currently selected Image to " + copiedPath);
		}
		
		/**
		 * Checks if something is chosen before then do nothing
		 * but if something is chose it will be deleted and removed from Model.
		 * 
		 * @throws IOException
		 */
		public void removeSelectedImage() throws IOException {
			ImageEntry choice = this.imageEntry.get();
			
			if (choice == null || Files.notExists(choice.getPath())) {
				this.log.info("Choice was empty. Deleting nothing.");
				return;
			}
			
			Files.delete(choice.getPath());
			this.log.info("Choice deleted from InputFolder.");
			
			this.imageEntries.get().remove(choice);
			this.log.info("Choice removed from internal Representation.");
			
			if (this.imageEntries.get().size() > 0) {
				this.imageEntry.set(this.imageEntries.get().get(0));
				this.log.info("ChoiceBox reset to first ImageEntry.");
			}
		}
		
		
		/**
		 * Iterates through the whole list,
		 * deletes the image
		 * and its reference
		 * 
		 * @throws IOException 
		 */
		public void resetImageDatabase() throws IOException {
			for (ImageEntry p : this.imageEntries.get()) {
				Files.delete(p.getPath());
			}
			this.imageEntries.get().clear();
		}
	
	
	// ==================================================
	// Getter Implementation
	// ==================================================
//	public Path getInputPath() { return this.inputPath; }
//	public Path getOutputPath() { return this.outputPath; }
//	public Path getImagePath() { return this.imagePath.get(); }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
//	@Override public void setImagePath(Path path) { this.imagePath.set(path); }
	
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<ImageEntry>> getImageEntriesProperty() { return this.imageEntries; }
	public ObjectProperty<ImageEntry> getImageEntryProperty() { return this.imageEntry; }
	
	public static class ImageEntry {
		private Mat imageMat;
		private Path imagePath;
		public static Converter CONVERTER;
		
		public ImageEntry(Path imagePath) {
			this.imageMat = Images.getMatFromPath(imagePath);
			this.imageMat = Images.toGrayScale(this.imageMat);
			this.imagePath = imagePath;
			ImageEntry.CONVERTER = new Converter();
		}
		
		public ImageEntry(Mat imageMat, Path imagePath) {
			this.imageMat = Images.toGrayScale(imageMat);
			this.imagePath = imagePath;
			ImageEntry.CONVERTER = new Converter();
		}
		
		public void store() {
			Images.store(this.imageMat, this.imagePath);
		}
		
		public Image getImage() {
			return Images.matToImage(this.imageMat);
		}
		
		public Mat getMat() {
			return this.imageMat;
		}
		
		public Path getPath() {
			return this.imagePath;
		}
		
		public String getName() {
			return this.imagePath.getFileName().toString();
		}
		
		private static class Converter extends StringConverter<ImageEntry> {
			@Override 
			public ImageEntry fromString(String string) {
				return null;
			}

			@Override
			public String toString(ImageEntry imageEntry) {
				return imageEntry.getName();
			}	
		}
	}
}
