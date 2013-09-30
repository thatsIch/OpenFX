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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.google.inject.Inject;

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
	
	final private ObjectProperty<ObservableList<ErrorEntry>> errors = new ChoiceBox<ErrorEntry>().itemsProperty();
	final private ObjectProperty<ErrorEntry> error = new SimpleObjectProperty<ErrorEntry>();
	
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
				this.errors.get().add(new ErrorEntry(child));
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + this.errors.get().size() + ".");
		
		if (this.errors.get().size() > 0) {
			this.error.set(this.errors.get().get(0));
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
		
		Mat error = Mat.zeros(image.size(), CvType.CV_8U);
		error = this.errorGenerator.get().generateError(error);
		this.log.info("Error generated.");
		
		String dateTime = this.errorGenerator.get().getName() + new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss-SSS").format(new Date());
		
		Path imagePath = this.errorFolderPath.resolve(dateTime + ".png");
		this.log.info("Path: " + imagePath);

		ErrorEntry entry = new ErrorEntry(image, error, imagePath);
		this.log.info("Instantiated ErrorEntry.");
		
		this.errors.get().add(entry);
		this.log.info("Error added to Model.");
		
		this.error.set(entry);
		this.log.info("Error set to current.");
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
//	public ObservableList<IErrorGenerator> getErrorGenerators() { return this.errorGenerators.get(); }
//	public IErrorGenerator getErrorGenerator() { return this.errorGenerator.get(); }
	
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
		// TODO Bild direkt speichern nach generieren
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
		
		public Mat getErrorMatrix(int frameSize) {
			Mat result = new Mat();
			
			return result;
		}
		
		public Mat getOriginalMat() {
			return this.originalMat;
		}
		
		public Mat getErrorMat() {
			return this.errorMat;
		}
		
		public String getName() {
			return this.storagePath.getFileName().toString();
		}
		
		private static class Converter extends StringConverter<ErrorEntry> {

			@Override
			public ErrorEntry fromString(String string) {
				return null;
			}

			@Override
			public String toString(ErrorEntry entry) {
				return entry.getName();
			}
			
		}
	}
}
