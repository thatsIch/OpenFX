package de.thatsich.bachelor.javafx.model;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.util.StringConverter;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.model.ImageDatabase.ImageEntry;
import de.thatsich.bachelor.opencv.error.LineError;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.Images;
import de.thatsich.core.opencv.error.IErrorGenerator;

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
		
		if (this.errorEntries.get().size() > 0) {
			this.errorEntry.set(this.errorEntries.get().get(0));
			this.log.info("Initialized with first Image.");
		}
	}
	
	/**
	 * Applies currently selected Error on the input image.
	 * 
	 * @param image Image which the Error is applied on.
	 */
	public void applyErrorOn(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale. " + image.type());
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");

		this.applyErrorOn(image, this.errorGenerator.get());
	}
	
	private void applyErrorOn(Mat image, IErrorGenerator generator) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale. " + image.type());
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		if (generator == null) throw new IllegalArgumentException("Generator is null.");
		
		this.log.info("Looping for " + this.getErrorLoopCount() + " Errors.");
		for (int errorCount = 0; errorCount < this.errorLoopCount.get(); errorCount++) {
			Mat error = Mat.zeros(image.size(), CvType.CV_8U);
			error = generator.generateError(error);
			this.log.info("Error generated.");
			
			String dateTime = this.errorGenerator.get().getName() + new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss-SSS").format(new Date());
			
			Path imagePath = this.errorFolderPath.resolve(dateTime + ".png");
			this.log.info("Path: " + imagePath);
	
			ErrorEntry entry = new ErrorEntry(image, error, imagePath);
			this.log.info("Instantiated ErrorEntry.");
			
			this.errorEntries.get().add(entry);
			this.log.info("Error added to Model.");
			
			this.errorEntry.set(entry);
			this.log.info("Error set to current.");
		}
	}
	
	/**
	 * Deletes all ErrorEntries from HDD and Model
	 * 
	 * @throws IOException If Image could not be deleted.
	 */
	public void resetErrorDatabase() throws IOException {
		for (ErrorEntry e : this.errorEntries.get()) {
			Files.delete(e.getPath());
		}
		this.errorEntries.get().clear();
	}
	
	/**
	 * Removes the selected ErrorEntry
	 * 
	 * @throws IOException Throws exception if file could not be deleted (for example if it is already deleted while the app is running)
	 */
	public void removeSelectedError() throws IOException {
		ErrorEntry entry = this.errorEntry.get();
		
		if (entry == null || Files.notExists(entry.getPath())) {
			this.log.info("Choice was empty. Deleting nothing.");
			return;
		}
		
		Files.delete(entry.getPath());
		this.log.info("Choice deleted from ErrorFolder.");
		
		this.errorEntries.get().remove(entry);
		this.log.info("Choice removed from internal Representation.");
		
		if (this.errorEntries.get().size() > 0) {
			this.errorEntry.set(this.errorEntries.get().get(0));
			this.log.info("ChoiceBox reset to first ErrorEntry.");
		}
	}
	
	/**
	 * Creates for every image in the database
	 * a set of errors
	 * 
	 * @param imageEntries
	 */
	public void permutateImageWithErrors(ObservableList<ImageEntry> imageEntries) {
		for (ImageEntry entry : imageEntries) {
			for (IErrorGenerator gen : this.errorGenerators.get()) {
				this.applyErrorOn(entry.getMat(), gen);
			}
		}
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
//	public ObservableList<IErrorGenerator> getErrorGenerators() { return this.errorGenerators.get(); }
//	public IErrorGenerator getErrorGenerator() { return this.errorGenerator.get(); }
	public int getErrorLoopCount() { return this.errorLoopCount.get(); }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
//	public void setErrorGenerators(ObservableList<IErrorGenerator> errorGenerators) { this.errorGenerators.set(errorGenerators); }
//	public void setErrorGenerator(IErrorGenerator generator) { this.errorGenerator.set(generator); }

	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<IErrorGenerator>> getErrorGeneratorsProperty() { return this.errorGenerators; }
	public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty() { return this.errorGenerator; }
	public ObjectProperty<ObservableList<ErrorEntry>> getErrorEntriesProperty() { return this.errorEntries; }
	public ObjectProperty<ErrorEntry> getErrorEntryProperty() { return this.errorEntry; }
	public IntegerProperty getErrorLoopCountProperty() { return this.errorLoopCount; }
	
	
	public static class ErrorEntry {
		private final Mat originalMat;
		private final Mat errorMat;
		private final Path storagePath;
		public static Converter CONVERTER = new Converter();
		
		
		/**
		 * Generating ErrorEntry CTOR
		 * 
		 * @param originalMat
		 * @param errorMat
		 * @param storagePath
		 */
		public ErrorEntry(Mat originalMat, Mat errorMat, Path storagePath) {
			this.originalMat = originalMat;
			this.errorMat = errorMat;
			this.storagePath = storagePath;
			
			// Creates a third channel as dummy because you cannot save a 2 channel image
			Mat dummyChan = Mat.zeros(this.originalMat.size(), CvType.CV_8U);
			List<Mat> listMat = Arrays.asList(this.originalMat, this.errorMat, dummyChan);
			Mat mergedMat = new Mat(originalMat.size(), CvType.CV_8UC3);
			Core.merge(listMat, mergedMat);
			
			Images.store(mergedMat, storagePath);	
		}
		
		/**
		 * Reading ErrorEntry CTOR
		 * 
		 * @param storagePath
		 */
		public ErrorEntry(Path storagePath) {
			this.storagePath = storagePath;
			
			Mat encodedImage = Images.getMatFromPath(storagePath);
			
			// split channels to extract GL and Error Mat
			List<Mat> encodedImageChannelMats = new ArrayList<Mat>(); 
			Core.split(encodedImage, encodedImageChannelMats);
			
			this.originalMat = encodedImageChannelMats.get(0);
			this.errorMat = encodedImageChannelMats.get(1);
		}
		
		// TODO implement getErrorMatrix
		public Mat getErrorMatrix(int frameSize) {
			Mat result = new Mat();
			
			return result;
		}
		
		// ==================================================
		// Getter Implementation
		// ==================================================
		public Mat getOriginalMat() { return this.originalMat; }
		public Mat getErrorMat() { return this.errorMat; }
		public String getName() { return this.storagePath.getFileName().toString(); }
		//TODO display the error matrix as grayscale completely with original image
		public Image getImage() { return Images.matToImage(errorMat); }
		public Path getPath() { return this.storagePath; }
		
		/**
		 * JavaFX Converter to display the file name in Control-Objects instead something random
		 * 
		 * @author Minh
		 */
		private static class Converter extends StringConverter<ErrorEntry> {
			@Override public ErrorEntry fromString(String string) { return null; }
			@Override public String toString(ErrorEntry entry) { return entry.getName(); }
		}
	}
}
