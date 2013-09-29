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

import com.google.inject.Inject;

import de.thatsich.core.Log;

public class ImageDatabase {

	// Fields
	final private Path inputPath;
	final private Path outputPath;
	
	// properties
	final private ObjectProperty<Path> imagePath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<ObservableList<Path>> imagePaths = new ChoiceBox<Path>().itemsProperty();
	
	// Injects
	final private Log log;
	
	@Inject
	private ImageDatabase(Log log) throws IOException {
		this.log = log;
		
		this.inputPath = Paths.get("input");
		this.outputPath = Paths.get("output");
		
		if (Files.notExists(this.inputPath) || !Files.isDirectory(this.inputPath)) Files.createDirectory(this.inputPath);
		if (Files.notExists(this.outputPath) || !Files.isDirectory(this.outputPath)) Files.createDirectory(this.outputPath); 
		
		this.initImagePaths();
	}
	
	/**
	 * Initialize the ImagePath variable 
	 * with all images in the input folder 
	 * supported by OpenCV and JavaFX (jpg, png)
	 */
	private void initImagePaths() {

		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.inputPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				this.imagePaths.get().add(child.toAbsolutePath());
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + this.imagePaths.get().size() + ".");
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
			
			Path newPath = this.inputPath.resolve(imagePath.getFileName());
			this.log.info("Created new Path: " + newPath);
			
			if (Files.exists(newPath)) {
				this.log.info("Duplicate found: File already exists.");
				return;
			}
					
			Path copiedPath = Files.copy(imagePath, newPath);
			this.log.info("Copied selection ("+ imagePath +") into InputFolder ("+this.inputPath+"): " + copiedPath);
			
			this.imagePaths.get().add(copiedPath);
			this.log.info("Added copy to ChoiceBoxDisplayImage: " + copiedPath.toString());
		}
		
		/**
		 * Checks if something is chosen before then do nothing
		 * but if something is chose it will be deleted and removed from Model.
		 * 
		 * @throws IOException
		 */
		public void removeSelectedImage() throws IOException {
			Path choice = this.imagePath.get();
			
			if (choice == null || Files.notExists(choice)) {
				this.log.info("Choice was empty. Deleting nothing.");
				return;
			}
			
			Files.delete(choice);
			this.log.info("Choice deleted from InputFolder.");
			
			this.imagePaths.get().remove(choice);
			this.log.info("Choice removed from internal Representation.");
		}
		
		
		/**
		 * Iterates through the whole list,
		 * deletes the image
		 * and its reference
		 * 
		 * @throws IOException 
		 */
		public void resetImageDatabase() throws IOException {
			for (Path p : this.imagePaths.get()) {
				Files.delete(p);
				this.imagePaths.get().remove(p);
			}
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
	public ObjectProperty<ObservableList<Path>> getImagePathsProperty() { return this.imagePaths; }
	public ObjectProperty<Path> getImagePathProperty() { return this.imagePath; }
}
