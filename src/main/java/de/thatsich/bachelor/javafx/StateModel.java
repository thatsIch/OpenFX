package de.thatsich.bachelor.javafx;

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

import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.bachelor.opencv.metric.EuclideanDistance;
import de.thatsich.bachelor.opencv.metric.ManhattenDistance;
import de.thatsich.bachelor.opencv.metric.MaximumDistance;
import de.thatsich.bachelor.opencv.metric.SquaredEuclideanDistance;
import de.thatsich.core.Log;
import de.thatsich.core.guice.PostInit;
import de.thatsich.core.opencv.error.IErrorGenerator;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;
import de.thatsich.core.opencv.metric.IMetric;


/**
 * Stores the State of the application.
 * 
 * @author Tran Minh Do
 *
 */
public class StateModel implements IStateModel, PostInit {

	// Fields
	final private Path inputPath;
	final private Path outputPath;
	
	// Properties
	final private ObjectProperty<ObservableList<Path>> imagePaths = new ChoiceBox<Path>().itemsProperty();
	final private ObjectProperty<ObservableList<IErrorGenerator>> errorGenerators = new ChoiceBox<IErrorGenerator>().itemsProperty();
	final private ObjectProperty<ObservableList<IMetric>> metrics = new ChoiceBox<IMetric>().itemsProperty();
	final private ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new ChoiceBox<IFeatureExtractor>().itemsProperty();
	
	final private ObjectProperty<Path> imagePath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<IErrorGenerator> errorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	final private ObjectProperty<IMetric> metric = new SimpleObjectProperty<IMetric>();
	final private ObjectProperty<IFeatureExtractor> featureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
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
		
		this.inputPath = Paths.get("input");
		this.outputPath = Paths.get("output");
		
		if (Files.notExists(this.inputPath) || !Files.isDirectory(this.inputPath)) Files.createDirectory(this.inputPath);
		if (Files.notExists(this.outputPath) || !Files.isDirectory(this.outputPath)) Files.createDirectory(this.outputPath); 
		
		this.initImagePaths();
		this.initErrorGenerators();
		this.initMetrics();
		this.initFeatureExtractors();
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
	
	private void initErrorGenerators() {
//		this.errorGenerators.get().addAll(
//			null
//		);
	}
	
	
	/**
	 * 
	 */
	private void initMetrics() {
		this.metrics.get().addAll(
			new EuclideanDistance(),
			new ManhattenDistance(),
			new MaximumDistance(),
			new SquaredEuclideanDistance()
		);
	}
	
	private void initFeatureExtractors() {
		this.featureExtractors.get().addAll(
			new Gradient(),
			new GrayLevelCooccurenceHistogram(),
			new HuMoments(),
			new LocalBinaryPatternHistogram(),
			new Mean(),
			new Variance()
		);
	}
	
	/*
	 * ==================================================
	 * PostInit Implementation
	 * ==================================================
	 */
	public void init() {
		
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
	@Override public void setMetrics(ObservableList<IMetric> metrics) { this.metrics.set(metrics); }
	@Override public void setFeatureExtractors(ObservableList<IFeatureExtractor> featureExtractors) { this.featureExtractors.set(featureExtractors); }
	
	@Override public void setImagePath(Path path) { this.imagePath.set(path); }
	@Override public void setErrorGenerator(IErrorGenerator generator) { this.errorGenerator.set(generator); }
	@Override public void setMetric(IMetric metric) { this.metric.set(metric); }
	@Override public void setFeatureExtractor(IFeatureExtractor featureExtractor) { this.featureExtractor.set(featureExtractor); }
	
	@Override public void setFrameSize(int size) { this.frameSize.set(size); }
	@Override public void setThreshold(int threshold) { this.threshold.set(threshold); }
	
	/*
	 * ==================================================
	 * Property Implementation
	 * ==================================================
	 */
	@Override public ObjectProperty<ObservableList<Path>> getImagePathsProperty() { return this.imagePaths; }
	@Override public ObjectProperty<ObservableList<IErrorGenerator>> getErrorGeneratorsProperty() { return this.errorGenerators; }
	@Override public ObjectProperty<ObservableList<IMetric>> getMetricsProperty() { return this.metrics; }
	@Override public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	
	@Override public ObjectProperty<Path> getImagePathProperty() { return this.imagePath; }
	@Override public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty() { return this.errorGenerator; }
	@Override public ObjectProperty<IMetric> getMetricProperty() { return this.metric; }
	@Override public ObjectProperty<IFeatureExtractor> getFeatureExtractorProperty() { return this.featureExtractor; }
	
	@Override public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	@Override public IntegerProperty getThresholdProperty() { return this.threshold; }
}
