package com.thatsich.sample.javafx.model;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;
import com.thatsich.core.java.Log;
import com.thatsich.core.opencv.error.generator.IErrorGenerator;
import com.thatsich.core.opencv.metric.IMetric;


/**
 * Stores the State of the application.
 * 
 * @author Tran Minh Do
 *
 */
public class StateModel implements IStateModel {

	// Fields
	final private Path inputPath;
	final private Path outputPath;

	// Properties
	final private ObjectProperty<ObservableList<Path>> imagePaths = new ChoiceBox<Path>().itemsProperty();
	final private ObjectProperty<ObservableList<IErrorGenerator>> errorGenerators = new ChoiceBox<IErrorGenerator>().itemsProperty();
	final private ObjectProperty<ObservableList<IMetric>> metrics = new ChoiceBox<IMetric>().itemsProperty();
	
	final private ObjectProperty<Path> imagePath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<IErrorGenerator> errorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	final private ObjectProperty<IMetric> metric = new SimpleObjectProperty<IMetric>();
	
	final private IntegerProperty frameSize = new SimpleIntegerProperty();
	final private IntegerProperty threshold = new SimpleIntegerProperty();
	
	// Injects
	final private Log log;
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Inject
	public StateModel(Log log) throws IOException {
		this.log = log;
		final Path parent = FileSystems.getDefault().getPath("");
		
		this.inputPath = parent.resolve("input").toAbsolutePath();
		this.outputPath = parent.resolve("output").toAbsolutePath();
		
		if (Files.notExists(this.inputPath) || !Files.isDirectory(this.inputPath)) Files.createDirectory(this.inputPath);
		if (Files.notExists(this.outputPath) || !Files.isDirectory(this.outputPath)) Files.createDirectory(this.outputPath); 
		
		this.initImagePaths();
	}
	
	/**
	 * Initialize the ImagePath variable 
	 * with all images in the input folder 
	 */
	private void initImagePaths() {

		System.out.println(this.log == null);
		// TODO : only files, possibly only image files
		// bmp, OpenCV offers support for the image formats Windows bitmap (bmp), portable image formats (pbm, pgm, ppm) and Sun raster (sr, ras). With help of plugins (you need to specify to use them if you build yourself the library, nevertheless in the packages we ship present by default) you may also load image formats like JPEG (jpeg, jpg, jpe), JPEG 2000 (jp2 - codenamed in the CMake as Jasper), TIFF files (tiff, tif) and portable network graphics (png). Furthermore, OpenEXR is also a possibility.
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.inputPath)) {
			for (Path child : stream) {
				this.imagePaths.get().add(child);
				this.log.info("Added " + child + " from input.");
			}	
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ==================================================
	 * Getter Implementation
	 * ==================================================
	 */
	@Override public Path getInputPath() { return this.inputPath; }
	@Override public Path getOutputPath() { return this.outputPath; }
	
	@Override public ObservableList<Path> getImagePaths() { return this.imagePaths.get(); }
	@Override public ObservableList<IErrorGenerator> getErrorGenerators() { return this.errorGenerators.get(); }
	@Override public ObservableList<IMetric> getMetrics() { return this.metrics.get(); }
	
	@Override public Path getImagePath() { return this.imagePath.get(); }
	@Override public IErrorGenerator getErrorGenerator() { return this.errorGenerator.get(); }
	@Override public IMetric getMetric() { return this.metric.get(); };
	
	@Override public int getFrameSize() { return this.frameSize.get(); }
	@Override public int getThreshold() { return this.threshold.get(); }
	
	
	/*
	 * ==================================================
	 * Setter Implementation
	 * ==================================================
	 */	
	@Override public void setImagePaths(ObservableList<Path> imagePaths) { this.imagePaths.set(imagePaths); }
	@Override public void setErrorGenerators(ObservableList<IErrorGenerator> errorGenerators) { this.errorGenerators.set(errorGenerators); }
	@Override public void setFrameSize(int size) { this.frameSize.set(size); }
	
	@Override public void setImagePath(Path path) { this.imagePath.set(path); }
	@Override public void setErrorGenerator(IErrorGenerator generator) { this.errorGenerator.set(generator); }
	@Override public void setMetric(IMetric metric) { this.metric.set(metric); }
	
	@Override public void setMetrics(ObservableList<IMetric> metrics) { this.metrics.set(metrics); }
	@Override public void setThreshold(int threshold) { this.threshold.set(threshold); }
	
	
	/*
	 * ==================================================
	 * Property Implementation
	 * ==================================================
	 */
	@Override public ObjectProperty<ObservableList<Path>> getImagePathsProperty() { return this.imagePaths; }
	@Override public ObjectProperty<ObservableList<IErrorGenerator>> getErrorGeneratorsProperty() { return this.errorGenerators; }
	@Override public ObjectProperty<ObservableList<IMetric>> getMetricsProperty() { return this.metrics; }
	
	@Override public ObjectProperty<Path> getImagePathProperty() { return this.imagePath; }
	@Override public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty() { return this.errorGenerator; }
	@Override public ObjectProperty<IMetric> getMetricProperty() { return this.metric; }
	
	@Override public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	@Override public IntegerProperty getThresholdProperty() { return this.threshold; }
}
